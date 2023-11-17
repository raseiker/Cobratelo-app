package com.example.cobratelo_app.ui.pay_history.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.model.toCanceledServiceUI
import com.example.cobratelo_app.data.model.toPendingServiceUI
import com.example.cobratelo_app.data.network.toServicePayHistory
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicePayHistoryViewModel @Inject constructor(
    private val servicePayHistoryRepository: ServicePayHistoryRepository,
) : ViewModel() {
    private var renterId: String? = null

    private var rentHistory: List<ServicePayHistory> = emptyList()

    private var _pendingServiceUI = MutableLiveData<MutableList<PendingServicePayUI>>()
    val pendingServicePayUI: LiveData<MutableList<PendingServicePayUI>> get() = _pendingServiceUI

    private var pendingBackUp: MutableList<PendingServicePayUI> = mutableListOf()


    fun setRenterId(renterId: String) {
        this.renterId = renterId
        setRentHistoryByRenterId(renterId)
        getPendingServicePayments()
        Log.d("setRenterId", "setRenterId: $renterId")
    }

    //get all rent history by Id
    private fun setRentHistoryByRenterId(renterId: String) {
        if( this.renterId != null)
            rentHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterId)
                .asLiveData().value
                ?.map { it.toServicePayHistory(renterId) }!!
    }

    private fun getPendingServicePayments() = renterId?.let { id ->

        //obtain all history service payments
        val serviceHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(id)
            .asLiveData().value
            ?.map { it.toServicePayHistory(id) }!!

        //filter by pending pays only
        val pendingService = serviceHistory.filter { !it.status }

        //create pending service UI
        pendingBackUp = pendingService.map { it.toPendingServiceUI() }.toMutableList()

        //enable first
        if (pendingBackUp.isNotEmpty()) pendingBackUp[0] =
            pendingBackUp[0].copy(enabled = true)

        //update UI
        _pendingServiceUI.value = pendingBackUp.toMutableList()
    }

    fun getCanceledServicePayments(): List<CanceledServicePayUI>? {
        return renterId?.let {id ->
            //get history by renter id
            val paymentHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(id)
                .asLiveData().value
                ?.map { it.toServicePayHistory(id) }

            //filter by canceled only
            val onlyCanceled = paymentHistory?.filter { it.status }

            //create list UI
            onlyCanceled?.map { it.toCanceledServiceUI() }
        } ?: emptyList()
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
}