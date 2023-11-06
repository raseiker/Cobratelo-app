package com.example.cobratelo_app.ui.renter_detail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.RenterStatus
import com.example.cobratelo_app.databinding.FragmentRenterDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RenterDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RenterDetailFragment()
    }

    private val fViewModel: RenterDetailViewModel by viewModels()
    private var binding : FragmentRenterDetailBinding? = null
    private val navArgs: RenterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRenterDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            //find renter by id
            val renterClicked = fViewModel.getRenterById(navArgs.renterId)

            //assign renter object with the renter data binding
            renter = renterClicked
            fragment = this@RenterDetailFragment

            //get pending renter payments by renterId
            //plus all pending payments
            //show result in the fragment via data binding
            viewModel = fViewModel

            rentPaymentCard.setOnClickListener {
                //navigate and pass in the renter name
                val action = RenterDetailFragmentDirections.actionRenterDetailFragmentToRentPayHistoryFragment(renterClicked.name, renterClicked.id)
                findNavController().navigate(action)
            }

            rentServicePaymentCard.setOnClickListener {
                val action = RenterDetailFragmentDirections.actionRenterDetailFragmentToServicePayHistoryFragment(renterClicked.name, renterClicked.id)
                findNavController().navigate(action)
            }
        }
    }

    fun setStatusColor(status: Boolean): ColorStateList {
        return if (status) {
            ContextCompat.getColorStateList(requireContext(), R.color.uptodate_status)!!
        } else {
            ContextCompat.getColorStateList(requireContext(), R.color.pending_status)!!
        }
    }

    fun setTextStatus(status: Boolean): String {
        return if (status) RenterStatus.UpToDate.label else RenterStatus.Pending.label
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}