package com.example.cobratelo_app.ui.pay_history.service

data class PendingServicePayUI(
    val id: String,//unique id
    val date: String,//from DB
    val energyAmount: String,//from DB
    val waterAmount: String,//from DB
    val checked: Boolean = false,//if true this checkbox checked, false otherwise
    val enabled: Boolean = false//if true this checkbox is enabled, false otherwise
)
