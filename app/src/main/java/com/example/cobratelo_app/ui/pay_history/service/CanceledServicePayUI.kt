package com.example.cobratelo_app.ui.pay_history.service

data class CanceledServicePayUI(
    val id: Int,
    val date: String,
    val energyAmount: String,
    val waterAmount: String,
    val action: Boolean = false//represents the download action
)
