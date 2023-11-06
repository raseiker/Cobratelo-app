package com.example.cobratelo_app.ui.renter_detail

import androidx.lifecycle.ViewModel
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RenterDetailViewModel @Inject constructor(
    private val renterRepository: RenterRepository,
    private val rentPayHistoryRepository: RentPayHistoryRepository,
    private val servicePayHistoryRepository: ServicePayHistoryRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    lateinit var renterRequested : Renter

    //find renter by id
    fun getRenterById(renterId: Int): Renter {
        renterRequested = renterRepository.getRenterById(renterId)
        return renterRequested
    }

    //get Pending rent payments
    fun getTotalPendingRentPayments(): String {
        //get all rent pay history by renter ID
        val renterPayHistory = rentPayHistoryRepository.getRentPayHistoryByRenterId(renterRequested.id)

        //count the number of data that its status is false, which means that it's pending
        val pendingPaymentsCount = renterPayHistory.count { !it.status }

        //return the multiplication between the rent amount and the count of pending
        return (renterRequested.rentAmount * pendingPaymentsCount).toString()
    }

    //get total pending water payments
    fun getTotalPendingWaterPayments(): String {
        //get all payment service history by renter id
        val allServiceHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterRequested.id)
        //filter by only pending
        val onlyPending = allServiceHistory.filter { !it.status }
        //sum all rows by only ones that have as a concept "agua"
        val totalWater = onlyPending.filter { it.concept == "agua" }.sumOf { it.amount }
        //return sum
        return totalWater.toString()
    }

    //get total pending energy payments
    fun getTotalPendingEnergyPayments(): String {
        //get all service payments history
        val allServiceHistory = servicePayHistoryRepository.getAllServicePayHistoryByRenterId(renterRequested.id)
        //filter by only pending and concept equal to <<luz>>
        val onlyEnergyPending = allServiceHistory.filter { !it.status }.filter { it.concept == "luz" }

        //sum it's amount attribute
        val totalEnergy = onlyEnergyPending.sumOf { it.amount }

        //return sum
        return totalEnergy.toString()
    }

    //get sum of energy, water and rent pending payments
    fun getTotal(rent: String, water: String, energy: String): String {
        return (rent.substring(4).toDouble() +
                water.substring(4).toDouble() +
                energy.substring(4).toDouble()).toString()
    }

}

fun main() {
    println(listOf(1,2,3,4).sumOf { it })
    println("$/. 68.0".substring(4))

}

