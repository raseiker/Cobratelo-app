package com.example.cobratelo_app.ui.pay_history.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.cobratelo_app.R
import com.example.cobratelo_app.databinding.FragmentCanceledServicePayBinding

class CanceledServicePayFragment : Fragment() {

    private var binding: FragmentCanceledServicePayBinding? = null
    private val fViewModel: ServicePayHistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCanceledServicePayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding ?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = fViewModel
            canceledServicePaymentsRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            canceledServicePaymentsRv.adapter = CanceledServiceRentPayAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}