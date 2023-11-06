package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.model.RentPayHistory

interface RentPayHistoryRepository {

    fun getAllRentPayHistory(): List<RentPayHistory>

    fun getRentPayHistoryByRenterId(renterId: Int): List<RentPayHistory>

    fun getLastCanceledPayment(): RentPayHistory?
}