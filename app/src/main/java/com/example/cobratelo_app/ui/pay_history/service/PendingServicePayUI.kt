package com.example.cobratelo_app.ui.pay_history.service

import com.example.cobratelo_app.data.model.ServicePayHistory

data class PendingServicePayUI(
    val id: String,//unique id
    val date: String,//from DB
    val energyAmount: String,//from DB
    val waterAmount: String,//from DB
    val checked: Boolean = false,//if true this checkbox checked, false otherwise
    val enabled: Boolean = false//if true this checkbox is enabled, false otherwise
)

fun PendingServicePayUI.getTotalService() = (energyAmount.toDouble() + waterAmount.toDouble())

//Make the convert only when the pay is going to be committed. Status is always true
fun PendingServicePayUI.toServicePayHistory(renterId: String) = ServicePayHistory (
    id = id,
    date = date,
    energyAmount = energyAmount.toDouble(),
    waterAmount = waterAmount.toDouble(),
    status = true,
    renterId = renterId
)
