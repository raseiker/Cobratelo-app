package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.ServicePayHistory

class ServicePayHistoryRepositoryImpl: ServicePayHistoryRepository {
    override fun getAllServicePayHistory(): List<ServicePayHistory> {
        return FakeSource.servicePayHistoryList.value!!
    }

    override fun getAllServicePayHistoryByRenterId(renterId: Int): List<ServicePayHistory> {
        return FakeSource.servicePayHistoryList.value?.filter { it.renterId == renterId }!!
    }
}