package com.example.cobratelo_app.ui.renter_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.toRentPayHistory
import com.example.cobratelo_app.data.network.toRenter
import com.example.cobratelo_app.data.network.toServicePayHistory
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RenterDetailViewModel @Inject constructor(
    private val renterRepository: RenterRepository,
    private val rentPayHistoryRepository: RentPayHistoryRepository,
    private val servicePayHistoryRepository: ServicePayHistoryRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    var renterRequested: LiveData<Renter?> = MutableLiveData()

    private var _totalWater = MutableLiveData(0.0)
    val totalWater: LiveData<Double> = _totalWater

    private var _totalEnergy = MutableLiveData(0.0)
    val totalEnergy: LiveData<Double> = _totalEnergy

    private var _total = MutableLiveData(0.0)
    val total: LiveData<Double> = _total

    //find renter by id
    fun setRenterById(renterId: String) {

        renterRequested =
            renterRepository.getRenterById(renterId).map { it?.toRenter(false, "") }.asLiveData()

        Log.d(TAG, "getRenterById: ${renterRequested.value}")
//        return renterRequested
    }

    //get Pending rent payments
    fun getTotalPendingRentPayments(): LiveData<String> = renterRequested.switchMap { renter ->
        liveData {
            //get all rent pay history by renter ID
            Log.d(TAG, "viewModel renter: $renter")
            val renterPayHistory =
                rentPayHistoryRepository.getRentPayHistoryByRenterId(renter?.id!!)
                    .firstOrNull()
                    ?.map { it.toRentPayHistory(renter.id) }

            Log.d(TAG, "viewModel rent history size: ${renterPayHistory}")

            //count the number of data that its status is false, which means that it's pending
            val pendingPaymentsCount = renterPayHistory?.count { !it.status } ?: 0

            //return the multiplication between the rent amount and the count of pending
            Log.d(TAG, "viewModel total rent: ${(renter.rentAmount * pendingPaymentsCount)}")

            //get total pending rent and then emit it
            val total = renter.rentAmount * pendingPaymentsCount

            //pass renter in
            val job = setPendingSummary(renter)

            //pass total rent in
            job.join()
            setTotal(total)

            //emit it to UI
            emit(total.toString())
        }
    }

    private fun setPendingSummary(renter: Renter) = viewModelScope.launch {

        //get all payment service history by renter id
        val allServiceHistory =
            servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renter.id)
                .firstOrNull()
                ?.map { it.toServicePayHistory(renter.id) }

        //filter by only pending
        val onlyPending = allServiceHistory?.filter { !it.status }

        //sum all rows by its water amount attribute and set it
        _totalWater.value = onlyPending?.sumOf { it.waterAmount } ?: 0.0
        Log.d(TAG, "viewModel total water: ${totalWater}")

        //sum it's energy amount attribute and set it
        _totalEnergy.value = onlyPending?.sumOf { it.energyAmount } ?: 0.0
    }

    private fun setTotal(totalPendingRent: Int) {
        _total.value = totalEnergy.value!! + totalWater.value!! + totalPendingRent
    }

}

fun main() {
    println(listOf(1, 2, 3, 4).sumOf { it })
    println("$/. 68.0".substring(4))

}

