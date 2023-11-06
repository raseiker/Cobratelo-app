package com.example.cobratelo_app.data.repo.consumption.general

import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.ui.ResponseUI

class EnergyConsumptionRepositoryImpl(

): EnergyConsumptionRepository {
    override fun getEnergyConsumption(): ResponseUI<EnergyConsumption> {
        return try {
            val energy = FakeSource.energyConsumption.value!!
            ResponseUI.Success(energy)
        } catch (e: Exception) {
            ResponseUI.Error("You don't set an energy consumption yet. Do you want to set up now ?")
        }
    }

    override fun updateEnergyConsumption(item: EnergyConsumption): Boolean {
        return try {
            FakeSource.energyConsumption.value = item
            true
        } catch (e: Exception) {
            false
        }
    }
}