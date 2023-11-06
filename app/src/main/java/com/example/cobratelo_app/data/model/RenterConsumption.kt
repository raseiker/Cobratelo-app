package com.example.cobratelo_app.data.model

data class RenterConsumption(
    val id: Int,//unique id
    val kwh: Int,//kwh consumptioned during this month
    val water: Double,//price that will be borrowed for this month consumption
    val date: String,//date that represent the month of this consumption
    val renterId: Int,//renter id of the owner of this consumption
)
