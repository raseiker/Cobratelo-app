package com.example.cobratelo_app.data.network

import com.example.cobratelo_app.data.model.Renter

data class RenterEntity(
    val id: String = "",
    val name: String = "",
    val contractTime: String = "",
    val createdAt: String = "",
    val flatType: String = "",
    val floor: String = "",
    val place: String = "",
    val rentAmount: Int = 0,
    val members: String = "",
    val rentPaymentDate: String = "",
    val servicesPaymentDate: String = "",
    val status: Boolean = false,
)

fun RenterEntity.toRenter(status: Boolean = false, desc: String = "") = Renter(
    id = id,
    name = name,
    contractTime = contractTime,
    createdAt = createdAt,
    flatType = flatType,
    floor = floor,
    members = members,
    place = place,
    rentAmount = rentAmount,
    rentPaymentDate = rentPaymentDate,
    servicesPaymentDate = servicesPaymentDate,
    status = status,
    desc = desc
)
