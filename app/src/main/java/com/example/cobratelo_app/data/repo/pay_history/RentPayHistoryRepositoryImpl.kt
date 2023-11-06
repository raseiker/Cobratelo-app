package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.model.getMonthValue

class RentPayHistoryRepositoryImpl(

): RentPayHistoryRepository {
    override fun getAllRentPayHistory(): List<RentPayHistory> {
        return FakeSource.rentPayHistoryList.value!!
    }

    override fun getRentPayHistoryByRenterId(renterId: Int): List<RentPayHistory> {
        return FakeSource.rentPayHistoryList.value?.filter { it.renterId == renterId }!!
    }

    override fun getLastCanceledPayment(): RentPayHistory? {
        return FakeSource.rentPayHistoryList.value
            ?.filter { it.status }
            ?.maxBy { it.getMonthValue() }
    }
}