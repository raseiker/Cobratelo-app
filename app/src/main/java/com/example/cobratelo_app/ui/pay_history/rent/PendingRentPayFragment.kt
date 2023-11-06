package com.example.cobratelo_app.ui.pay_history.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.cobratelo_app.databinding.FragmentPendingRentPayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingRentPayFragment : Fragment() {

    private var binding: FragmentPendingRentPayBinding? = null
    private val fViewModel: PayHistoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendingRentPayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = fViewModel
            lifecycleOwner = viewLifecycleOwner
            pendingPaymentsRv.adapter = PendingRentPayAdapter(PendingRentPayListener { item, checked ->
                    fViewModel.onCheckedChange(item, checked)
                    payButton.isEnabled = fViewModel.enablePayOption()
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}