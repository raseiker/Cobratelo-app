package com.example.cobratelo_app.ui.consumption

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.network.EnergyConsumptionEntity
import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class UpdateGeneralConsumptionViewModel @Inject constructor(
    private val energyConsumptionRepository: EnergyConsumptionRepository
): ViewModel() {
    // TODO: Implement the ViewModel

    //update general energy consumption
    fun insertGeneralEnergyConsumption(
        place: String,
        energyCharge: String,
        unitValue: String,
        date: String,
        total: String,
    ) = viewModelScope.async {
        val newItem = EnergyConsumptionEntity(
            place = place,
            energyCharge = energyCharge.toDouble(),
            unitValue = unitValue.toDouble(),
            date = date,
            total = total.toDouble()
        )

        //save into db
        Log.d(TAG, "new energy consumption in viewModel: $newItem")
        energyConsumptionRepository.insertEnergyConsumption(newItem)
    }
}