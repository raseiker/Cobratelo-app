package com.example.cobratelo_app.ui.pay_history.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.cobratelo_app.core.PAY_HISTORY_TABS
import com.example.cobratelo_app.databinding.FragmentRentPayHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentPayHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = RentPayHistoryFragment()
    }

    private val viewModel: PayHistoryViewModel by activityViewModels()
    private val navArgs: RentPayHistoryFragmentArgs by navArgs()
    private var binding: FragmentRentPayHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentPayHistoryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            //set renter name by
            nameTxt.text = navArgs.renterName
            viewModel.setRenterId(navArgs.renterId)

            //set view pager 2 adapter
            viewPager.adapter = RentViewPageAdapter(requireActivity())

            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = PAY_HISTORY_TABS[position]
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}