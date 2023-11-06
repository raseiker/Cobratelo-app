package com.example.cobratelo_app.ui.home.listener

class HomeFragmentListener(
    private val cardAction: (renterId: Int) -> Unit
) {
    fun onCardClicked(renterId: Int) = cardAction(renterId)
}