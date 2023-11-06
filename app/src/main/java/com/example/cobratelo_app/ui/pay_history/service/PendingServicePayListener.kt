package com.example.cobratelo_app.ui.pay_history.service

class PendingServicePayListener(
    private val listener: (PendingServicePayUI, Boolean) -> Unit
) {
    fun onCheckedChange(item: PendingServicePayUI, checked: Boolean) = listener(item, checked)
}