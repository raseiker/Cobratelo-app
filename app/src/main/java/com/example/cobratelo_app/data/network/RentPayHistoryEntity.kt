package com.example.cobratelo_app.data.network

import com.example.cobratelo_app.data.model.RentPayHistory

data class RentPayHistoryEntity(
    val id: String = "",
    val date: String = "",
    val status: Boolean = false//if it's up to date, then is TRUE, if it's pending, then is FALSE
)

fun RentPayHistoryEntity.toRentPayHistory(renterId: String) = RentPayHistory(
    id = id,
    date = date,
    status = status,
    renterId = renterId
)
