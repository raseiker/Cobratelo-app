package com.example.cobratelo_app.data.model

import com.example.cobratelo_app.core.Util
import com.example.cobratelo_app.core.getPrettyDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Renter(
    val id: String,
    val name: String,
    val place: String,
    val desc: String = "",//this is new
    val createdAt: String,
    val status: Boolean = true,//by default a renter is up to date
    val contractTime: String,
    val members: String,
    val floor: String,
    val flatType: String,
    val rentPaymentDate: String,
    val servicesPaymentDate: String,
    val rentAmount: Int,
)

fun Renter.getServicesPaymentDay() = setDateToFormat(servicesPaymentDate, Util().formatter)

fun Renter.getRentPaymentDay() = setDateToFormat(rentPaymentDate, Util().formatter)

private fun setDateToFormat(date: String, formatter: DateTimeFormatter): String {
    return LocalDate.parse(date, formatter).dayOfMonth.getPrettyDay()


//    val calendar = Calendar.getInstance()
//    calendar.time = formatter.parse(date)!!
//    return calendar.get(Calendar.DAY_OF_MONTH).getPrettyDay()
}

