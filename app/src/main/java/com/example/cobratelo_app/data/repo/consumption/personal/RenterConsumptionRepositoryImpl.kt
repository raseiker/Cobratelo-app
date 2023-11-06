package com.example.cobratelo_app.data.repo.consumption.personal

import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RenterConsumption

class RenterConsumptionRepositoryImpl(): RenterConsumptionRepository {
    override fun updateConsumption(newItem: RenterConsumption): Boolean {
        return try {
           FakeSource.renterConsumption.value = FakeSource.renterConsumption.value?.plus(newItem)
           true
        } catch (e: Exception) {
            false
        }
    }

    override fun getConsumptionByRenterId(renterId: Int): RenterConsumption {
        return FakeSource.renterConsumption.value?.first { it.renterId == renterId }!!
    }
}