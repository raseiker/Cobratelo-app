package com.example.cobratelo_app.ui.home.listener

class HomeFragmentListener(
    private val cardAction: (renterId: String) -> Unit
) {
    fun onCardClicked(renterId: String) = cardAction(renterId)
}