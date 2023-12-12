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
            //set renter by its id
            fViewModel.setRenterById(navArgs.renterId)

            //assign renter object with the renter data binding
            fViewModel.renterRequested.observe(viewLifecycleOwner) { item ->
                renter = item
            }
            fragment = this@RenterDetailFragment

            //observe and set total pending rent
            fViewModel.getTotalPendingRentPayments().observe(viewLifecycleOwner) {total ->
                rentAmountTxt.text = getString(R.string.renter_summary_amount, total)
            }

            //observe and set total pending water
            fViewModel.totalWater.observe(viewLifecycleOwner){ totalWater ->
                waterAmountTxt.text = getString(R.string.renter_summary_amount, totalWater.toString())
            }

            //observe and set total pending energy
            fViewModel.totalEnergy.observe(viewLifecycleOwner){ totalEnergy ->
                energyAmountTxt.text = getString(R.string.renter_summary_amount, totalEnergy.toString())
            }

            //observe and set sum of pending energy + water + rent
            fViewModel.total.observe(viewLifecycleOwner){total ->
                totalAmountTxt.text = getString(R.string.renter_summary_amount, total.toString())
            }

            rentPaymentCard.setOnClickListener {
                fViewModel.renterRequested.value?.let { renter ->
                    //navigate and pass in the renter name
                    val action = RenterDetailFragmentDirections
                        .actionRenterDetailFragmentToRentPayHistoryFragment(
                            renterName = renter.name!!,
                            renterId = renter.id!!,
                            renterAmount = renter.rentAmount
                        )
                    findNavController().navigate(action)
                }
            }

            rentServicePaymentCard.setOnClickListener {
                (fViewModel.renterRequested.value)?.let { renter ->
                    val action = RenterDetailFragmentDirections
                        .actionRenterDetailFragmentToServicePayHistoryFragment(
                            renterName = renter.name!!,
                            renterId = renter.id!!
                        )
                    findNavController().navigate(action)
                }
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