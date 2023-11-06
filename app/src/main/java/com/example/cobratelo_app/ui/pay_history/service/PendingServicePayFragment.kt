package com.example.cobratelo_app.ui.pay_history.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.cobratelo_app.databinding.FragmentPendingServicePayBinding

class PendingServicePayFragment : Fragment() {

    private var binding: FragmentPendingServicePayBinding? = null
    private val fViewModel: ServicePayHistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendingServicePayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = fViewModel
            lifecycleOwner = viewLifecycleOwner
            pendingServicePaymentsRv.adapter = PendingServicePayAdapter(PendingServicePayListener{ item, checked ->
                fViewModel.onCheckedChangeService(item, checked)
                onPayPendingPayments.isEnabled = fViewModel.enablePayOption()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}