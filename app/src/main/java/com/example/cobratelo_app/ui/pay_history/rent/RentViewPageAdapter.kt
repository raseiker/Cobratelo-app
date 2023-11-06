package com.example.cobratelo_app.ui.pay_history.rent

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cobratelo_app.core.PAY_HISTORY_TABS
import com.example.cobratelo_app.ui.pay_history.rent.CanceledRentPayFragment
import com.example.cobratelo_app.ui.pay_history.rent.PendingRentPayFragment

class RentViewPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return PAY_HISTORY_TABS.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PendingRentPayFragment()
            1 -> CanceledRentPayFragment()
            else -> PendingRentPayFragment()
        }
    }
}