package com.example.cobratelo_app.data.network

import com.example.cobratelo_app.data.model.EnergyConsumption

data class EnergyConsumptionEntity(
    val id: String = "",
    val place: String = "",
    val energyCharge: Double = 0.0,
    val unitValue: Double = 0.0,
    val date: String = "",
    val total: Double = 0.0,
)

fun EnergyConsumptionEntity.toEnergyConsumption() = EnergyConsumption(
    id = id,
    place = place,
    energyCharge = energyCharge,
    unitValue = unitValue,
    date = date,
    total = total
)
