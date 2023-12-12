package com.example.cobratelo_app.ui.energy_summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.model.toEnergyConsumptionUI
import com.example.cobratelo_app.data.network.toEnergyConsumption
import com.example.cobratelo_app.databinding.FragmentEnergySummaryBinding
import com.example.cobratelo_app.ui.ResponseUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnergySummaryFragment : Fragment() {

    companion object {
        fun newInstance() = EnergySummaryFragment()
    }

    private val viewModel: EnergySummaryViewModel by viewModels()
    private var binding: FragmentEnergySummaryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnergySummaryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            //viewModel.getEnergyConsumption()
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.stateUI.collect { stateUI ->
                        when (stateUI) {
                            is ResponseUI.Error -> showAddEnergyConsumptionDialog()
                            ResponseUI.Loading -> Log.d(TAG, "im loading in energy fragment")
                            is ResponseUI.Success -> {
                                energy = stateUI.data.toEnergyConsumption().toEnergyConsumptionUI()
                                onNavigateToUpdateGeneralConsumption.setOnClickListener {
                                    findNavController().navigate(R.id.action_energySummaryFragment_to_updateGeneralConsumptionFragment)
                                }

                                onNavigateToUpdateRenterConsumption.setOnClickListener {
                                    findNavController().navigate(R.id.action_energySummaryFragment_to_updateRenterConsumptionFragment)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAddEnergyConsumptionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_energy_title)
            .setMessage(R.string.add_energy_message)
            .setCancelable(false)
            .setPositiveButton(R.string.add_energy_positive) { _, _ ->
                findNavController().navigate(R.id.action_energySummaryFragment_to_updateGeneralConsumptionFragment)
            }
            .setNegativeButton(R.string.add_energy_negative) { _, _ ->
                findNavController().navigateUp()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}