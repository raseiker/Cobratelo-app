package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.network.ServicePayHistoryEntity
import kotlinx.coroutines.flow.Flow

interface ServicePayHistoryRepository {

    fun getAllServicePayHistory(): List<ServicePayHistory>

    fun getAllServicePayHistoryByRenterId(renterId: String): Flow<List<ServicePayHistoryEntity>>
}