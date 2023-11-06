package com.example.cobratelo_app.ui.consumption

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateGeneralConsumptionViewModel @Inject constructor(
    private val energyConsumptionRepository: EnergyConsumptionRepository
): ViewModel() {
    // TODO: Implement the ViewModel

    //update general energy consumption
    fun updateGeneralEnergyConsumption(
        place: String,
        energyCharge: String,
        unitValue: String,
        date: String,
        total: String,
    ): Boolean {
        val newItem = EnergyConsumption(
            id = 1,
            place = place,
            energyCharge = energyCharge.toDouble(),
            unitValue = unitValue.toDouble(),
            date = date,
            total = total.toDouble()
        )

        //save into db
        Log.d("onGeneralConsumption", "updateGeneralEnergyConsumption: $newItem")
        return energyConsumptionRepository.updateEnergyConsumption(newItem)
    }
}