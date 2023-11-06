package com.example.cobratelo_app.ui.pay_history.rent

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PendingRentPayUI(
    val id: Int,//id for the DiffUtil
    val date: String,//date of the pat. It comes from database
    val amount: String,//amount of the pay. It comes from database
    val checked: Boolean = false,//if this checkbox is checked or not
    val enabled: Boolean = false,//if this checkbox is enabled or not
)

fun PendingRentPayUI.onItemClicked() = copy(checked = !checked)

fun PendingRentPayUI.toDate(): Date {
    return SimpleDateFormat("d/MM/y", Locale.getDefault()).parse(date)!!
}