package com.example.cobratelo_app.ui.consumption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.PLACE_LIST
import com.example.cobratelo_app.core.generateMenuDropdown
import com.example.cobratelo_app.core.getPlaceItems
import com.example.cobratelo_app.core.showDatePicker
import com.example.cobratelo_app.core.validateFields
import com.example.cobratelo_app.databinding.FragmentUpdateGeneralConsumptionBinding
import dagger.hilt.android.AndroidEntryPoint

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

            validateFields(listOf(tvPlace, tvTotal, tvUnitValue, tvChargeEnergy, tvGeneralConsumptionDate), updateBtn)

            generateMenuDropdown(place, PLACE_LIST)

            generalConsumptionDate.setEndIconOnClickListener {
                showDatePicker(tvGeneralConsumptionDate)
            }

            updateBtn.setOnClickListener {
                val response = updateGeneralEnergyConsumption(
                    tvPlace.text.toString(),
                    tvChargeEnergy.text.toString(),
                    tvUnitValue.text.toString(),
                    tvGeneralConsumptionDate.text.toString(),
                    tvTotal.text.toString()
                )
                if (response) {
                    //show confirmation message
                    //back to energy summary
                    findNavController().navigateUp()
                } else {
                    //show error message
                }
            }
        }
    }

    private fun updateGeneralEnergyConsumption(
        place: String,
        energyCharge: String,
        unitValue: String,
        date: String,
        total: String,
    ) = viewModel.updateGeneralEnergyConsumption(place, energyCharge, unitValue, date, total)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}