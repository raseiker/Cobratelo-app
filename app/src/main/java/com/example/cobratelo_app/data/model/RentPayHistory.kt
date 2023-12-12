package com.example.cobratelo_app.data.model

import com.example.cobratelo_app.core.Util
import com.example.cobratelo_app.data.network.RentPayHistoryEntity
import com.example.cobratelo_app.ui.pay_history.rent.CanceledRentPayUI
import com.example.cobratelo_app.ui.pay_history.rent.PendingRentPayUI
import java.time.LocalDate

data class RentPayHistory(
    val id: String,
    val date: String,
    val status: Boolean,//if it's up to date, then is TRUE, if it's pending, then is FALSE
    val renterId: String,//foreign key of Renter data class
)

fun RentPayHistory.toPendingRentUI(amount: String) = PendingRentPayUI(
    id = id,
    date = date,
    amount = amount,
)

fun RentPayHistory.toCanceledRentUI(amount: String) = CanceledRentPayUI (
    date = date,
    amount = amount,
)

fun RentPayHistory.toRentPayHistoryEntity() = RentPayHistoryEntity(
    id = id,
    date = date,
    status = status,
)

fun RentPayHistory.getMonthValue(): Int {
    val dateFormatted = LocalDate.parse(date, Util().formatter)
    return dateFormatted.monthValue
}