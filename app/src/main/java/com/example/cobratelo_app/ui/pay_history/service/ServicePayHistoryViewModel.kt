package com.example.cobratelo_app.ui.pay_history.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.model.toCanceledServiceUI
import com.example.cobratelo_app.data.model.toPendingServiceUI
import com.example.cobratelo_app.data.model.toRentPayHistoryEntity
import com.example.cobratelo_app.data.model.toServicePayHistoryEntity
import com.example.cobratelo_app.data.network.toServicePayHistory
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import com.example.cobratelo_app.ui.ResponseUI
import com.example.cobratelo_app.ui.pay_history.pay_confirmation.rent.RentPayConfirmationUI
import com.example.cobratelo_app.ui.pay_history.rent.toRentPayHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ServicePayHistoryViewModel @Inject constructor(
    private val servicePayHistoryRepository: ServicePayHistoryRepository,
) : ViewModel() {
    var renterId: String? = null
        private set
    var renterName: String? = null
        private set

    private var rentHistory: List<ServicePayHistory> = emptyList()

    private var _pendingServiceUI = MutableLiveData<MutableList<PendingServicePayUI>>()
    val pendingServicePayUI: LiveData<MutableList<PendingServicePayUI>> get() = _pendingServiceUI

    private var pendingBackUp: MutableList<PendingServicePayUI> = mutableListOf()

    private var _stateUI = MutableStateFlow<ResponseUI<Boolean>>(ResponseUI.Loading)
    val stateUI: StateFlow<ResponseUI<Boolean>> = _stateUI.asStateFlow()

    fun setRenterId(renterId: String, renterName: String)= viewModelScope.launch {
        this@ServicePayHistoryViewModel.renterId = renterId
        this@ServicePayHistoryViewModel.renterName = renterName
        val job = setServiceHistoryByRenterId(renterId)
        job.join()
        getPendingServicePayments()
        Log.d("setRenterId", "setRenterId: $renterId")
    }

    //get all rent history by Id
    private fun setServiceHistoryByRenterId(renterId: String) = viewModelScope.launch {
            rentHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterId)
                .firstOrNull()
                ?.map { it.toServicePayHistory(renterId) } ?: emptyList()
    }

    private fun getPendingServicePayments() {

            //filter by pending pays only
            val pendingService = rentHistory.filter { !it.status }

            //create pending service UI
            pendingBackUp = pendingService.map { it.toPendingServiceUI() }.toMutableList()

            //enable first
            if (pendingBackUp.isNotEmpty()) pendingBackUp[0] =
                pendingBackUp[0].copy(enabled = true)

            //update UI
            _pendingServiceUI.value = pendingBackUp.toMutableList()

    }

    fun getCanceledServicePayments(): List<CanceledServicePayUI>? {

            //filter by canceled only
            val onlyCanceled = rentHistory.filter { it.status }

            //create list UI
            return onlyCanceled?.map { it.toCanceledServiceUI() }
    }

    fun enablePayOption(): Boolean {
        return _pendingServiceUI.value?.any { it.checked }!!
    }

    fun onCheckedChangeService(item: PendingServicePayUI, checked: Boolean) {
        val selectedIndex = pendingBackUp.indexOf(item)
        if (checked) {
            //check selected
            replaceItem(item.copy(checked = true), "check selected")

            //enable next if exists
            setNextRow(selectedIndex, true, "enable newer")

            //disable previous if exists
            setPreviousRow(selectedIndex, false, "disable older")
        } else {
            //uncheck selected
            replaceItem(item.copy(checked = false), "uncheck selected")

            //disable next if exists
            setNextRow(selectedIndex, false, "disable newer")

            //enable previous if exists
            setPreviousRow(selectedIndex, true, "enable older")
        }
        _pendingServiceUI.value = pendingBackUp.toMutableList()
    }

    private fun replaceItem(item: PendingServicePayUI, msg: String) {
        val itemIndex = pendingBackUp.indexOfFirst { it.id == item.id }
        pendingBackUp[itemIndex] = item
        Log.d("onCheckedChange", pendingBackUp.toString() + msg)
    }

    private fun setPreviousRow(index: Int, enable: Boolean, msg: String) {
        if (index - 1 >= 0) {
            val previous = pendingBackUp[index - 1]
            replaceItem(previous.copy(enabled = enable), "$msg older")
        }
    }
    private fun setNextRow(index: Int, enable: Boolean, msg: String) {
        if (index + 1 <= pendingBackUp.size - 1) {
            val next = pendingBackUp[index + 1]
            replaceItem(next.copy(enabled = enable), "$msg newer")
        }
    }

    fun checkedPendingList() = pendingBackUp.filter { it.checked }
        .map { RentPayConfirmationUI(
            id = it.id,
            date = it.date,
            concept = "LUZ-AGUA",
            amount = it.getTotalService().toString()
        ) }
    fun getCurrentTime(): String = LocalDateTime.now().format(
        DateTimeFormatter
        .ofPattern("dd/M/yyyy HH:m"))

    fun getTotalSelectedPays(): String = pendingBackUp.filter { it.checked }
        .sumOf { it.getTotalService() }.toString()

    fun confirmPay() = viewModelScope.launch {
        //catch only checked rent pending
        val selectedPays = pendingBackUp.filter { it.checked }

        //call repo
        _stateUI.value = servicePayHistoryRepository.updatePendingServices (
            selectedList = selectedPays.map { it.toServicePayHistory(renterId!!) }
                .map { it.toServicePayHistoryEntity() },
            renterId = renterId!!
        )
        //return boolean and react accordingly
    }
}