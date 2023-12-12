package com.example.cobratelo_app.data.network

data class RenterConsumptionEntity(
    val id: String = "",//unique id
    val kwh: Int = 0,//kwh consumptioned during this month
    val water: Double = 0.0,//price that will be borrowed for this month consumption
    val date: String = "",//date that represent the month of this consumption
)