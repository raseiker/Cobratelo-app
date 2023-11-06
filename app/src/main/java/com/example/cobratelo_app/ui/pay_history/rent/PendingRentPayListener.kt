package com.example.cobratelo_app.ui.pay_history.rent

class PendingRentPayListener(
    val checkedListener: (PendingRentPayUI,Boolean) -> Unit
) {
    fun onCheckedChange(item: PendingRentPayUI,checked: Boolean) = checkedListener(item, checked)
}