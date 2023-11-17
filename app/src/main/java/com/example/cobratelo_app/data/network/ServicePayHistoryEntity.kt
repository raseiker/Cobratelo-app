package com.example.cobratelo_app.data.network

import com.example.cobratelo_app.data.model.ServicePayHistory

data class ServicePayHistoryEntity(
    val id: String,
    val date: String,
    val energyAmount: Double = 0.0,
    val waterAmount: Double = 0.0,
    val status: Boolean = false
)

fun ServicePayHistoryEntity.toServicePayHistory(renterId: String) = ServicePayHistory(
    id = id,
    date = date,
    energyAmount = energyAmount,
    waterAmount = waterAmount,
    status = status,
    renterId = renterId
)
