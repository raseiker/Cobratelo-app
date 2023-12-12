package com.example.cobratelo_app.ui.consumption

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
import com.example.cobratelo_app.core.PLACE_LIST
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.core.generateMenuDropdown
import com.example.cobratelo_app.core.showDatePicker
import com.example.cobratelo_app.core.validateFields
import com.example.cobratelo_app.databinding.FragmentUpdateGeneralConsumptionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateGeneralConsumptionFragment : Fragment() {


    private val viewModel: UpdateGeneralConsumptionViewModel by viewModels()
    private var binding: FragmentUpdateGeneralConsumptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateGeneralConsumptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            validateFields(
                listOf(
                    tvPlace,
                    tvTotal,
                    tvUnitValue,
                    tvChargeEnergy,
                    tvGeneralConsumptionDate
                ), updateBtn
            )

            generateMenuDropdown(place, PLACE_LIST)

            generalConsumptionDate.setEndIconOnClickListener {
                showDatePicker(tvGeneralConsumptionDate)
            }

            updateBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        val response = insertGeneralEnergyConsumption(
                            tvPlace.text.toString(),
                            tvChargeEnergy.text.toString(),
                            tvUnitValue.text.toString(),
                            tvGeneralConsumptionDate.text.toString(),
                            tvTotal.text.toString()
                        )
                        if (response.await()) {
                            //show confirmation message
                            Log.d(TAG, "energy consumption added in fragment: yes")
                            //back to energy summary
                            findNavController().navigateUp()
                        } else {
                            //show error message
                            Log.d(TAG, "energy consumption added in fragment doesn't occur")
                        }
                    }
                }
            }
        }
    }

    private fun insertGeneralEnergyConsumption(
        place: String,
        energyCharge: String,
        unitValue: String,
        date: String,
        total: String,
    ) = viewModel.insertGeneralEnergyConsumption(place, energyCharge, unitValue, date, total)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}