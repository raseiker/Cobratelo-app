package com.example.cobratelo_app.data.repo.consumption.general

import com.example.cobratelo_app.data.network.EnergyConsumptionEntity
import com.example.cobratelo_app.ui.ResponseUI
import kotlinx.coroutines.flow.Flow

interface EnergyConsumptionRepository {

    //get unique energy consumption
    fun getEnergyConsumption(): Flow<ResponseUI<EnergyConsumptionEntity>>

    //insert unique energy consumption
    suspend fun insertEnergyConsumption(item: EnergyConsumptionEntity): Boolean

}