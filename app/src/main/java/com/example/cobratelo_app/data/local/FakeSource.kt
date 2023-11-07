package com.example.cobratelo_app.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.model.RenterConsumption
import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.model.getMonthValue
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalField
import java.util.Calendar
import java.util.Locale

object FakeSource {

    val renterList = MutableLiveData(
        listOf(
            Renter(
                id = 1,
                name = "Nicolas",
                place = "Encantada",
                desc = "77 kwh consumido al 07/23",
                createdAt = "11/08/23",
                status = false,
                contractTime = "3",
                members = "1",
                floor = "3",
                flatType = "Minidepartamento",
                rentPaymentDate = "11/08/23",
                servicesPaymentDate = "12/08/23",
                rentAmount = 500,
            ),
            Renter(
                id = 2,
                name = "Reyna",
                place = "Encantada",
                desc = "80 kwh consumido al 07/23",
                createdAt = "01/08/23",
                status = true,
                contractTime = "6",
                members = "2",
                floor = "2",
                flatType = "Minidepartamento",
                rentPaymentDate = "01/08/23",
                servicesPaymentDate = "05/08/23",
                rentAmount = 350,
            ),
            Renter(
                id = 3,
                name = "Marco",
                place = "Bocanegra",
                desc = "05 kwh consumido al 11/23",
                createdAt = "11/10/23",
                status = false,
                contractTime = "12",
                members = "5",
                floor = "2",
                flatType = "Departamento",
                rentPaymentDate = "11/10/23",
                servicesPaymentDate = "12/10/23",
                rentAmount = 550,
            ),
            Renter(
                id = 4,
                name = "Iker",
                place = "Bocanegra",
                desc = "05 kwh consumido al 11/23",
                createdAt = "11/10/23",
                status = true,
                contractTime = "3",
                members = "2",
                floor = "4",
                flatType = "Minidepartamento",
                rentPaymentDate = "11/10/23",
                servicesPaymentDate = "11/10/23",
                rentAmount = 650,
            ),
            Renter(
                id = 5,
                name = "Edita",
                place = "Bocanegra",
                desc = "05 kwh consumido al 11/23",
                createdAt = "02/05/23",
                status = false,
                contractTime = "3",
                members = "2",
                floor = "2",
                flatType = "Habitación",
                rentPaymentDate = "02/05/23",
                servicesPaymentDate = "05/05/23",
                rentAmount = 800,
            )
        )
    )

    val rentPayHistoryList = MutableLiveData(
        listOf(
            RentPayHistory(
                id = 1,
                date = "11/07/23",
                status = true,
                renterId = 5
            ),
            RentPayHistory(
                id = 2,
                date = "11/08/23",
                status = false,
                renterId = 5
            ),
            RentPayHistory(
                id = 3,
                date = "11/09/23",
                status = false,
                renterId = 5
            ),
            RentPayHistory(
                id = 4,
                date = "11/10/23",
                status = true,
                renterId = 5
            ),
            RentPayHistory(
                id = 5,
                date = "11/11/23",
                status = false,
                renterId = 5
            )
        )
    )

    val servicePayHistoryList = MutableLiveData(
        listOf(
            ServicePayHistory(
                id = 1,
                date = "11/01/23",
                energyAmount = 75.0,
                waterAmount = 25.0,
                status = false,
                renterId = 5
            ),
            ServicePayHistory(
                id = 2,
                date = "11/02/23",
                energyAmount = 60.0,
                waterAmount = 40.5,
                status = false,
                renterId = 5
            ),
            ServicePayHistory(
                id = 3,
                date = "11/03/23",
                energyAmount = 62.0,
                waterAmount = 40.0,
                status = false,
                renterId = 5
            ),
            ServicePayHistory(
                id = 4,
                date = "11/04/23",
                energyAmount = 65.0,
                waterAmount = 50.0,
                status = true,
                renterId = 5
            ),
            ServicePayHistory(
                id = 5,
                date = "11/05/23",
                energyAmount = 70.0,
                waterAmount = 45.0,
                status = true,
                renterId = 5
            ),
            ServicePayHistory(
                id = 6,
                date = "11/03/23",
                energyAmount = 50.0,
                waterAmount = 45.0,
                status = false,
                renterId = 4
            ),
        )
    )

    val renterConsumption = MutableLiveData(
        listOf<RenterConsumption>(

        )
    )

    val energyConsumption: MutableLiveData<EnergyConsumption> = MutableLiveData(
//        EnergyConsumption(
//        id = 1,
//        place = "Encantada",
//        energyCharge = 120.0,
//        unitValue = 0.7,
//        date = "11/07/23",
//        total = 200.0
//        )
    )
}

fun main() {
    val rentPendingDate = "05/08/23"
    val lastCanceled = "05/11/22"
    val formatter = DateTimeFormatter.ofPattern("d/MM/yy", Locale.getDefault())
    val currentDate = LocalDate.now()

    val lastCanceledDateFormatted = LocalDate.parse(rentPendingDate, formatter)
    val interval = Period.between(lastCanceledDateFormatted, currentDate)

    //lastCanceled exists ?
    // if yes, use lasCanceled as parameter
    //if not, use rentPending as parameter
    //Are there months of difference ?
    //if yes, count them and insert pending payments according to it
    //if not, Are there years of difference ?
    //if yes, insert twelve pending payments
    //if not, do anything
    interval.months.apply {
        if (this > 0) {
            for (i in 1..this ) {
                println("insert pending payment number: $i")
            }
        }
    }
    println(interval.months)
    println(interval.years)

    val result = FakeSource.rentPayHistoryList.value
        ?.filter { it.status }
        ?.maxBy { it.getMonthValue() }

    println(result)
}