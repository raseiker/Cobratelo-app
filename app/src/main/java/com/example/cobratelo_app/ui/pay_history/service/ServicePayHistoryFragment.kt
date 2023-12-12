package com.example.cobratelo_app.ui.pay_history.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.PAY_HISTORY_TABS
import com.example.cobratelo_app.databinding.FragmentServicePayHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [ServicePayHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServicePayHistoryFragment : Fragment() {


    private var binding: FragmentServicePayHistoryBinding ? = null
    private val viewModel: ServicePayHistoryViewModel by activityViewModels()
    private val args: ServicePayHistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentServicePayHistoryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            nameTxt.text = args.renterName
            viewModel.setRenterId(args.renterId, args.renterName)

            viewPager.adapter = ServiceViewPageAdapter(requireActivity())
            //set up tab layout mediator between tabLayout and viewPager and then attach it
            TabLayoutMediator(tabLayout, viewPager) {tab, position ->
                tab.text = PAY_HISTORY_TABS[position]
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}