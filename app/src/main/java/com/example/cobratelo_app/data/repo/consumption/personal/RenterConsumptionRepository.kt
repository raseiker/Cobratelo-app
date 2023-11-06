package com.example.cobratelo_app.data.repo.consumption.personal

import com.example.cobratelo_app.data.model.RenterConsumption

interface RenterConsumptionRepository {

    //update renter consumption
    fun updateConsumption(newItem: RenterConsumption): Boolean

    //get renter consumption by renter id
    fun getConsumptionByRenterId(renterId: Int): RenterConsumption
}