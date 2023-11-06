package com.example.cobratelo_app.data.model

import com.example.cobratelo_app.ui.pay_history.service.CanceledServicePayUI
import com.example.cobratelo_app.ui.pay_history.service.PendingServicePayUI


data class ServicePayHistory(
    val id: Int,//unique id
    val date: String,//from DB
    val concept: String,//from DB
    val amount: Double,//from DB
    val status: Boolean,//if it's up to date, then is TRUE, if it's pending, then is FALSE
    val renterId: Int,//foreign key of Renter data class
)

fun ServicePayHistory.toPendingServiceUI() = PendingServicePayUI(
    id = ("${renterId}${id}").toInt(),
    date = date,
    amount = amount.toString(),
    concept = concept.uppercase(),
)

fun ServicePayHistory.toCanceledServiceUI() = CanceledServicePayUI(
    id = ("${renterId}${id}").toInt(),
    date = date,
    amount = amount.toString(),
    concept = concept.uppercase()
)
