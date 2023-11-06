package com.example.cobratelo_app.ui.pay_history.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cobratelo_app.core.PAY_HISTORY_TABS

class ServiceViewPageAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return PAY_HISTORY_TABS.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingServicePayFragment()
            1 -> CanceledServicePayFragment()
            else -> PendingServicePayFragment()
        }
    }
}