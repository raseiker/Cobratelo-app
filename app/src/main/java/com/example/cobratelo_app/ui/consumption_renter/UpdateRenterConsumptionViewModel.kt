package com.example.cobratelo_app.ui.consumption_renter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RenterConsumption
import com.example.cobratelo_app.data.network.RenterConsumptionEntity
import com.example.cobratelo_app.data.network.toRenter
import com.example.cobratelo_app.data.repo.consumption.personal.RenterConsumptionRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UpdateRenterConsumptionViewModel @Inject constructor (
    private val renterRepository: RenterRepository,
    private val renterConsumptionRepository: RenterConsumptionRepository
) : ViewModel() {

    private var renterList = listOf<Pair<String,String>>()

    //get all renter names
    fun getAllRenterNames() = viewModelScope.async {
        //get all renter
        val renterNameList = renterRepository.getAllRenter()
            .first()
            .map { it.toRenter() }

        //set a map based on id and name of the renter list response
        renterList = renterNameList.map { Pair(it.id, it.name) }
        Log.d(TAG, "renterList in update renter consumption fragment: $renterList")

        //filter by name and return it
        renterList.map { it.second }
    }

    //update renter consumption
    fun updateRenterConsumption(renterName: String, kwh: String, water: String, date: String)
    = viewModelScope.async {

        //filter renter by name and get its renter id
        val renterId = renterList.first { it.second == renterName.trim() }.first

        //create renter consumption object
        val newConsumption = RenterConsumptionEntity(
            kwh = kwh.toInt(),
            date = date,
            water = water.toDouble(),
        )

        renterConsumptionRepository.updateConsumption(newConsumption, renterId)
//        if (update) {
//            Log.d("newConsumption", "UpdateRenterConsumption: ${FakeSource.renterConsumption.value}")
//        }

    }
}