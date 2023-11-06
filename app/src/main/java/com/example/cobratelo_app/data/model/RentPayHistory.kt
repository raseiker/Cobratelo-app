package com.example.cobratelo_app.data.model

import com.example.cobratelo_app.core.Util
import com.example.cobratelo_app.ui.pay_history.rent.CanceledRentPayUI
import com.example.cobratelo_app.ui.pay_history.rent.PendingRentPayUI
import java.time.LocalDate

data class RentPayHistory(
    val id: Int,
    val date: String,
    val status: Boolean,//if it's up to date, then is TRUE, if it's pending, then is FALSE
    val renterId: Int,//foreign key of Renter data class
)

fun RentPayHistory.toPendingRentUI(amount: String) = PendingRentPayUI(
    id = ("${renterId}${id}").toInt(),
    date = date,
    amount = amount,
)

fun RentPayHistory.toCanceledRentUI(amount: String) = CanceledRentPayUI (
    date = date,
    amount = amount,
)

fun RentPayHistory.getMonthValue(): Int {
    val dateFormatted = LocalDate.parse(date, Util().formatter)
    return dateFormatted.monthValue
}