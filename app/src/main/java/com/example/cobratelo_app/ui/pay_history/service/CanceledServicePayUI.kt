package com.example.cobratelo_app.ui.pay_history.service

data class CanceledServicePayUI(
    val id: Int,
    val concept: String,
    val date: String,
    val amount: String,
    val action: Boolean = false//represents the download action
)
