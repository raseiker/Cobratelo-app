package com.example.cobratelo_app.ui.pay_history.rent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.model.toCanceledRentUI
import com.example.cobratelo_app.data.model.toPendingRentUI
import com.example.cobratelo_app.data.model.toRentPayHistoryEntity
import com.example.cobratelo_app.data.network.toRentPayHistory
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.ui.ResponseUI
import com.example.cobratelo_app.ui.pay_history.pay_confirmation.rent.RentPayConfirmationUI
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
class PayHistoryViewModel @Inject constructor(
    private val rentPayHistoryRepository: RentPayHistoryRepository,
) : ViewModel() {
    // TODO: Implement the ViewModel
    var renterId: String? = null
        private set
    var renterAmount: Int? = null
        private set
    var renterName: String? = null
        private set

    private var _rentHistory: List<RentPayHistory>? = null

    private var _pendingRentPayUi = MutableLiveData<MutableList<PendingRentPayUI>>()
    val pendingRentPayUI: LiveData<MutableList<PendingRentPayUI>> get() = _pendingRentPayUi

    private var _pending : MutableList<PendingRentPayUI> = mutableListOf()
    val pending: List<PendingRentPayUI> get() = _pending

    private var _stateUI: MutableStateFlow<ResponseUI<Boolean>> = MutableStateFlow(ResponseUI.Loading)
    val stateUI: StateFlow<ResponseUI<Boolean>> = _stateUI.asStateFlow()

    fun setRenterDetails(renterId: String, renterAmount: Int, renterName: String)= viewModelScope.launch {
        this@PayHistoryViewModel.renterId = renterId
        this@PayHistoryViewModel.renterAmount = renterAmount
        this@PayHistoryViewModel.renterName = renterName
        val job = setRentHistoryByRenterId(renterId)
        job.join()
        getPendingRentPayments()
        Log.d("setRenterId", "setRenterId: $renterId")
    }

    //insert new pending payments based on the current date
    fun insertPendingPaymentsBasedOnCurrentDate() {
        //lastCanceled exists ?
        // if yes, use lasCanceled as parameter
        //if not, use rentPending as parameter
        //Are there months of difference ?
        //if yes, count them and insert pending payments according to it
        //if not, Are there years of difference ?
        //if yes, insert twelve pending payments
        //if not, do anything

    }

    //get all rent history by Id
    private fun setRentHistoryByRenterId(renterId: String) = viewModelScope.launch {
        _rentHistory = rentPayHistoryRepository.getRentPayHistoryByRenterId(renterId)
            .firstOrNull()
            ?.map { it.toRentPayHistory(renterId) } ?: emptyList()
    }

    //search list of rent pay history by id
    private fun getPendingRentPayments() = renterId?.let {
        viewModelScope.launch {
            //get rent pending payments by renter id
            val list = _rentHistory?.filter { !it.status }

            //create pending rent pay UI list
            _pending = list?.map { it.toPendingRentUI(renterAmount!!.toString()) } as MutableList

            //find newer and enable it
            if (pending.isNotEmpty()) _pending[0] = _pending[0].copy(enabled = true)

            _pendingRentPayUi.value = pending.toMutableList()
        }
    }

    fun getCanceledRentPayments(): List<CanceledRentPayUI>? = renterId?.let {
            //get canceled rent payments by its status
            val list = _rentHistory?.filter { it.status }

            list?.map { it.toCanceledRentUI(renterAmount!!.toString()) }
    }

    fun enablePayOption(): Boolean {
        return _pendingRentPayUi.value?.any { it.checked }!!
    }

    private fun replaceItem(item: PendingRentPayUI, msg: String) {
        val itemIndex = _pending.indexOfFirst { it.date == item.date }
        _pending[itemIndex] = item
        Log.d(TAG, "checked change: $_pending$msg")
    }


    //pending fragment logic
    fun onCheckedChange(selected: PendingRentPayUI, checked: Boolean) {
        //find selected index
        val selectedIndex = pending.indexOf(selected)

        if (checked) {
            //check selected
            replaceItem(selected.copy(checked = true), "check selected")

            //find next to the selected and enable it only if exits
            setNextRow(selectedIndex, true, "enable")

            //find previous than selected and disable it only if exists
            setPreviousRow(selectedIndex, false, "disable")

        } else {
            //unchecked selected
            replaceItem(selected.copy(checked = false), "uncheck selected")

            //find next to selected and disable it only if exists
            setNextRow(selectedIndex, false, "disable")

            //find previous to selected and enabled it only if exists
            setPreviousRow(selectedIndex, true, "enable")
        }

        //update UI
        _pendingRentPayUi.value = pending.toMutableList()
    }

    private fun setPreviousRow(index: Int, enable: Boolean, msg: String) {
        if (index - 1 >= 0) {
            val previous = pending[index - 1]
            replaceItem(previous.copy(enabled = enable), "$msg older")
        }
    }
    private fun setNextRow(index: Int, enable: Boolean, msg: String) {
        if (index + 1 <= pending.size - 1) {
            val next = pending[index + 1]
            replaceItem(next.copy(enabled = enable), "$msg newer")
        }
    }

    fun checkedPendingList() = pending.filter { it.checked }
        .map { RentPayConfirmationUI(
            id = it.id,
            date = it.date,
            concept = "RENTA",
            amount = it.amount
        ) }
    fun getCurrentTime(): String = LocalDateTime.now().format(DateTimeFormatter
        .ofPattern("dd/M/yyyy HH:m"))

    fun getTotalSelectedPays(): String = pending.filter { it.checked }
        .sumOf { it.amount.toInt() }
        .toString()

    fun confirmPay() = viewModelScope.launch {
        //catch only checked rent pending
        val selectedPays = pending.filter { it.checked }

        //call repo
        _stateUI.value = rentPayHistoryRepository.updatePendingRent (
            selectedList = selectedPays.map { it.toRentPayHistory(renterId!!) }
                .map { it.toRentPayHistoryEntity() },
            renterId = renterId!!
        )
        //return boolean and react accordingly
    }

}

fun main() {
    val time = LocalDateTime.now()

    println(time.format(DateTimeFormatter.ofPattern("dd/M/yyyy HH:m")))
}
