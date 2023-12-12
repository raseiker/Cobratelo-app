package com.example.cobratelo_app.ui.pay_history.pay_confirmation.service

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.databinding.FragmentServicePayConfirmationBinding
import com.example.cobratelo_app.ui.ResponseUI
import com.example.cobratelo_app.ui.pay_history.pay_confirmation.rent.RentPayConfirmationAdapter
import com.example.cobratelo_app.ui.pay_history.service.ServicePayHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ServicePayConfirmationFragment : Fragment() {


    private val viewModel by activityViewModels<ServicePayHistoryViewModel>()
    private val args by navArgs<ServicePayConfirmationFragmentArgs>()
    private var binding: FragmentServicePayConfirmationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicePayConfirmationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            model = viewModel
            renterNameTxt.text = args.renterName
            selectedPaysRv.adapter = RentPayConfirmationAdapter()
            (selectedPaysRv.adapter as RentPayConfirmationAdapter)
                .submitList(viewModel.checkedPendingList())

            payButton.setOnClickListener {
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        viewModel.confirmPay()
                        viewModel.stateUI.collect{ state ->
                            when (state) {
                                is ResponseUI.Error -> Log.d(
                                    TAG,
                                    "pay confirmation fragment error: ${state.message}"
                                )
                                ResponseUI.Loading -> progressBar.visibility = View.VISIBLE
                                is ResponseUI.Success -> {
                                    val action = ServicePayConfirmationFragmentDirections
                                        .actionServicePayConfirmationFragmentToServicePayHistoryFragment(
                                            renterName = viewModel.renterName!!,
                                            renterId = viewModel.renterId!!
                                        )
                                    findNavController().navigate(action)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}