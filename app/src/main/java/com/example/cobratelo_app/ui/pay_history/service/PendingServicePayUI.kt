package com.example.cobratelo_app.ui.pay_history.service

data class PendingServicePayUI(
    val id: Int,//unique id
    val date: String,//from DB
    val amount: String,//from DB
    val concept: String,//from DB
    val checked: Boolean = false,//if true this checkbox checked, false otherwise
    val enabled: Boolean = false//if true this checkbox is enabled, false otherwise
)
