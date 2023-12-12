package com.example.cobratelo_app.ui.pay_history.pay_confirmation.rent

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
import com.example.cobratelo_app.databinding.FragmentPayConfirmationBinding
import com.example.cobratelo_app.ui.ResponseUI
import com.example.cobratelo_app.ui.pay_history.rent.PayHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RentPayConfirmationFragment : Fragment() {


    private val viewModel by activityViewModels<PayHistoryViewModel>()
    private var binding: FragmentPayConfirmationBinding? = null
    private val navArgs by navArgs<RentPayConfirmationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayConfirmationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            selectedPaysRv.adapter = RentPayConfirmationAdapter()
            (selectedPaysRv.adapter as RentPayConfirmationAdapter).submitList(viewModel.checkedPendingList())
            renterNameTxt.text = navArgs.renterName
            model = viewModel

            payButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        viewModel.confirmPay()
                        viewModel.stateUI.collect { state ->
                            when (state) {
                                is ResponseUI.Error -> Log.d(
                                    TAG,
                                    "pay confirmation fragment error: ${state.message}"
                                )
                                ResponseUI.Loading -> progressBar.visibility = View.VISIBLE
                                is ResponseUI.Success -> {
                                    val action = RentPayConfirmationFragmentDirections
                                        .actionPayConfirmationFragmentToRentPayHistoryFragment (
                                            renterId = viewModel.renterId!!,
                                            renterName = viewModel.renterName!!,
                                            renterAmount = viewModel.renterAmount!!
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