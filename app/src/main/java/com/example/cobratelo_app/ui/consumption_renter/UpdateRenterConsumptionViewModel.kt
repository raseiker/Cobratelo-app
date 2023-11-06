package com.example.cobratelo_app.ui.consumption_renter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RenterConsumption
import com.example.cobratelo_app.data.repo.consumption.personal.RenterConsumptionRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UpdateRenterConsumptionViewModel @Inject constructor (
    private val renterRepository: RenterRepository,
    private val renterConsumptionRepository: RenterConsumptionRepository
) : ViewModel() {

    //get all renter names
    fun getAllRenterNames(): List<String>? {
        //get all renter
        val renterList = renterRepository.getAllRenter().value

        //filter by name and return it
        return renterList?.map { it.name }.also {
            Log.d("newConsumption", "UpdateRenterConsumption: $it")
        }
    }

    //update renter consumption
    fun updateRenterConsumption(renterName: String, kwh: String, water: String, date: String): Boolean {

        //get renter by name an get its renter id
        val renterSelected = renterRepository.getRenterByName(renterName)

        //create renter consumption object
        val newConsumption = RenterConsumption(
            id = Random.nextInt(renterSelected.id,20),
            kwh = kwh.toInt(),
            date = date,
            water = water.toDouble(),
            renterId = renterSelected.id
        )

        return renterConsumptionRepository.updateConsumption(newConsumption)
//        if (update) {
//            Log.d("newConsumption", "UpdateRenterConsumption: ${FakeSource.renterConsumption.value}")
//        }

    }
}