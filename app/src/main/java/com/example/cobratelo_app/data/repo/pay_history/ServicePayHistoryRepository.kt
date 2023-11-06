package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.model.ServicePayHistory

interface ServicePayHistoryRepository {

    fun getAllServicePayHistory(): List<ServicePayHistory>

    fun getAllServicePayHistoryByRenterId(renterId: Int): List<ServicePayHistory>
}