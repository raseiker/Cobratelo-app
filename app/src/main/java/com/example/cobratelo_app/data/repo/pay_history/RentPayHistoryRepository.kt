package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.network.RentPayHistoryEntity
import com.example.cobratelo_app.ui.ResponseUI
import kotlinx.coroutines.flow.Flow

interface RentPayHistoryRepository {

    fun getAllRentPayHistory(): List<RentPayHistory>

    fun getRentPayHistoryByRenterId(renterId: String): Flow<List<RentPayHistoryEntity>>

    fun getLastCanceledPayment(): RentPayHistory?

    suspend fun updatePendingRent(selectedList: List<RentPayHistoryEntity>, renterId: String): ResponseUI<Boolean>
}