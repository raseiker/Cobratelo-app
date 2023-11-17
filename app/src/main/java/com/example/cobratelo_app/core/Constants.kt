package com.example.cobratelo_app.core

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val CONTRACT_TIME_LIST = listOf("3", "6", "12")

val MEMBERS_LIST = listOf("1","2","3","4","5")

val PLACE_LIST = listOf("Bocanegra", "Encantada")

val FLOOR_LIST = listOf("1","2","3","4")

val FLAT_TYPE_LIST = listOf("Departamento", "Minidepartamento", "Habitación")

val PAY_HISTORY_TABS = listOf("Pendiente", "Cancelado")

const val TAG = "myLog"
sealed class RenterStatus(
    val label: String
) {
    object Pending: RenterStatus("PENDIENTE")
    object UpToDate: RenterStatus("AL DIA")
}