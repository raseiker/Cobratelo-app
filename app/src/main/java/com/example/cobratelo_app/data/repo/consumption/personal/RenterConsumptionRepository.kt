package com.example.cobratelo_app.data.repo.consumption.personal

import com.example.cobratelo_app.data.model.RenterConsumption
import com.example.cobratelo_app.data.network.RenterConsumptionEntity

interface RenterConsumptionRepository {

    //update renter consumption
    suspend fun updateConsumption(newItem: RenterConsumptionEntity, renterId: String): Boolean

    //get renter consumption by renter id
    fun getConsumptionByRenterId(renterId: Int): RenterConsumption
}