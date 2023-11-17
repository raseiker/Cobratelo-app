package com.example.cobratelo_app.ui.renter_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RenterDetailViewModel @Inject constructor(
    private val renterRepository: RenterRepository,
    private val rentPayHistoryRepository: RentPayHistoryRepository,
    private val servicePayHistoryRepository: ServicePayHistoryRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    private var renterRequested : MutableLiveData<Renter?> = MutableLiveData(null)
    var _renterRequested: LiveData<Renter?> = MutableLiveData()

    val totalRentPending = _renterRequested.switchMap {renter ->
        liveData{
            Log.d(TAG, "viewModel renter: $renter")
            val renterPayHistory = rentPayHistoryRepository.getRentPayHistoryByRenterId(renter?.id!!)
                .first()
                .map { it.toRentPayHistory(renter.id) }
//                        .map { it -> it.map { it.toRentPayHistory(renter.id) } }
//                        .asLiveData()}


            Log.d(TAG, "viewModel rent history size: ${renterPayHistory}")

            //count the number of data that its status is false, which means that it's pending
            val pendingPaymentsCount = renterPayHistory.count { !it.status }

            //return the multiplication between the rent amount and the count of pending
            Log.d(TAG, "viewModel total rent: ${(renter.rentAmount * pendingPaymentsCount)}")


            emit((renter.rentAmount * pendingPaymentsCount).toString())
        }
    }

    //find renter by id
    fun getRenterById(renterId: String)  {

        _renterRequested = renterRepository.getRenterById(renterId).map { it?.toRenter() }.asLiveData()

        Log.d(TAG, "getRenterById: ${_renterRequested.value}")
//        return renterRequested
    }

    fun setRenter(renter: Renter?) {

    }

    //get Pending rent payments
    fun getTotalPendingRentPayments(): LiveData<String> = _renterRequested.switchMap { renter ->
        liveData {
            //get all rent pay history by renter ID




                Log.d(TAG, "viewModel renter: $renter")
                val renterPayHistory = rentPayHistoryRepository.getRentPayHistoryByRenterId(renter?.id!!)
                        .first()
                        .map { it.toRentPayHistory(renter.id) }
//                        .map { it -> it.map { it.toRentPayHistory(renter.id) } }
//                        .asLiveData()}


                Log.d(TAG, "viewModel rent history size: ${renterPayHistory}")

                //count the number of data that its status is false, which means that it's pending
                val pendingPaymentsCount = renterPayHistory.count { !it.status }

                //return the multiplication between the rent amount and the count of pending
                Log.d(TAG, "viewModel total rent: ${(renter.rentAmount * pendingPaymentsCount)}")


            emit((renter.rentAmount * pendingPaymentsCount).toString())
        }
    }

    //get total pending water payments
    fun getTotalPendingWaterPayments(): String {
        //get all payment service history by renter id
//        val allServiceHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterRequested?.id!!)
//            .asLiveData().value
//            ?.map { it.toServicePayHistory(renterRequested?.id!!) }
//
//        //filter by only pending
//        val onlyPending = allServiceHistory?.filter { !it.status }
//        //sum all rows by its water amount attribute
//        val totalWater = onlyPending?.sumOf { it.waterAmount }
//        //return sum
//        return totalWater.toString()
        return "999"
    }

    //get total pending energy payments
    fun getTotalPendingEnergyPayments(): String {
        //get all service payments history
//        val allServiceHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterRequested?.id!!)
//            .asLiveData().value
//            ?.map { it.toServicePayHistory(renterRequested?.id!!) }
//
//        //filter by only pending
//        val onlyEnergyPending = allServiceHistory?.filter { !it.status }
//
//        //sum it's energy amount attribute
//        val totalEnergy = onlyEnergyPending?.sumOf { it.energyAmount }
//
//        //return sum
//        return totalEnergy.toString()
        return "999"
    }

    //get sum of energy, water and rent pending payments
    fun getTotal(rent: String, water: String, energy: String): String {
        return try {
            (rent.substring(4).toDouble() +
                    water.substring(4).toDouble() +
                    energy.substring(4).toDouble()).toString()
        } catch (e: NumberFormatException) {
            "0.0"
        }

    }

}

fun main() {
    println(listOf(1,2,3,4).sumOf { it })
    println("$/. 68.0".substring(4))

}

