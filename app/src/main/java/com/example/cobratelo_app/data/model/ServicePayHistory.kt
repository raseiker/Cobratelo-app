package com.example.cobratelo_app.data.model

import com.example.cobratelo_app.ui.pay_history.service.CanceledServicePayUI
import com.example.cobratelo_app.ui.pay_history.service.PendingServicePayUI


data class ServicePayHistory(
    val id: String,//unique id
    val date: String,//from DB
    val energyAmount: Double,//from DB
    val waterAmount: Double,//from DB
    val status: Boolean,//if it's up to date, then is TRUE, if it's pending, then is FALSE
    val renterId: String,//foreign key of Renter data class
)

fun ServicePayHistory.toPendingServiceUI() = PendingServicePayUI(
    id = "${renterId}${id}",
    date = date,
    energyAmount = energyAmount.toString(),
    waterAmount = waterAmount.toString(),
)

fun ServicePayHistory.toCanceledServiceUI() = CanceledServicePayUI(
    id = "${renterId}${id}",
    date = date,
    energyAmount = energyAmount.toString(),
    waterAmount = waterAmount.toString()
)
