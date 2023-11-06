package com.example.cobratelo_app.ui.pay_history.rent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.model.toCanceledRentUI
import com.example.cobratelo_app.data.model.toPendingRentUI
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayHistoryViewModel @Inject constructor(
    private val rentPayHistoryRepository: RentPayHistoryRepository,
    private val renterRepository: RenterRepository,
) : ViewModel() {
    // TODO: Implement the ViewModel
    private var renterId: Int = -1
    private var _rentHistory: List<RentPayHistory> = emptyList()
    val rentHistory get() = _rentHistory

    private var _pendingRentPayUi = MutableLiveData<MutableList<PendingRentPayUI>>()
    val pendingRentPayUI: LiveData<MutableList<PendingRentPayUI>> get() = _pendingRentPayUi

    private var _pending : MutableList<PendingRentPayUI> = mutableListOf()
    val pending: List<PendingRentPayUI> get() = _pending

    fun setRenterId(renterId: Int) {
        this.renterId = renterId
        setRentHistoryByRenterId(renterId)
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


    //search list of rent pay history by id
    private fun getPendingRentPayments() {
        if (renterId != -1) {
            //get rent amount by its id
            val renterAmount = renterRepository.getRenterById(renterId).rentAmount

            //get rent pending payments by renter id
            val list = _rentHistory.filter { !it.status }

            //create pending rent pay UI list
            _pending = list.map { it.toPendingRentUI(renterAmount.toString()) } as MutableList

            //find newer and enable it
            if (pending.isNotEmpty()) _pending[0] = _pending[0].copy(enabled = true)

            _pendingRentPayUi.value = pending.toMutableList()
        }
    }

    fun getCanceledRentPayments(): List<CanceledRentPayUI>?{
        return if (renterId != -1) {
            //get rent amount by renter id
            val renterAmount = renterRepository.getRenterById(renterId).rentAmount

            //get canceled rent payments by its status
            val list = _rentHistory.filter { it.status }

            list.map { it.toCanceledRentUI(renterAmount.toString()) }
        } else {
            emptyList()
        }
    }

    //get all rent history by Id
    private fun setRentHistoryByRenterId(renterId: Int) {
        if( renterId != -1)
            _rentHistory = rentPayHistoryRepository.getRentPayHistoryByRenterId(renterId)
    }

    fun enablePayOption(): Boolean {
        return _pendingRentPayUi.value?.any { it.checked }!!
    }

    private fun replaceItem(item: PendingRentPayUI, msg: String) {
        val itemIndex = _pending.indexOfFirst { it.date == item.date }
        _pending[itemIndex] = item
        Log.d("onCheckedChange", _pending.toString() + msg)
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


}
