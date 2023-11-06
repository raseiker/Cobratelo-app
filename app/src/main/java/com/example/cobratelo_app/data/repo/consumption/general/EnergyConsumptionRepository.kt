package com.example.cobratelo_app.data.repo.consumption.general

import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.ui.ResponseUI

interface EnergyConsumptionRepository {

    //get unique energy consumption
    fun getEnergyConsumption(): ResponseUI<EnergyConsumption>

    //update unique energy consumption
    fun updateEnergyConsumption(item: EnergyConsumption): Boolean

}