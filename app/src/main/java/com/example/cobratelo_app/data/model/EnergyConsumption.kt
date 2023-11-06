package com.example.cobratelo_app.data.model

import android.icu.util.LocaleData
import com.example.cobratelo_app.core.Util
import com.example.cobratelo_app.core.getPrettyMonth
import com.example.cobratelo_app.ui.energy_summary.EnergyConsumptionUI
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

data class EnergyConsumption(
    val id: Int,
    val place: String,
    val energyCharge: Double,
    val unitValue: Double,
    val date: String,
    val total: Double,
){
//    val calendar: Calendar = Calendar.getInstance()
}

fun EnergyConsumption.getMonth(): String {
    return LocalDate.parse(date, Util().formatter).month
        .getDisplayName(TextStyle.FULL, Locale.getDefault())
        .uppercase()
//    calendar.time = Util().formatter.parse(date)
//    return calendar.get(Calendar.MONTH)
}

fun EnergyConsumption.toEnergyConsumptionUI() = EnergyConsumptionUI(
    date = getMonth(),
    energyCharge = energyCharge.toString(),
    unitValue = unitValue.toString(),
    place = place,
    total = total.toString()
)
