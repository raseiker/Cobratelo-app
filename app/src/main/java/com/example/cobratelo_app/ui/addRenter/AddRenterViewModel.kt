package com.example.cobratelo_app.ui.addRenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.core.Util
import com.example.cobratelo_app.core.getPrettyDay
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.RenterEntity
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRenterViewModel @Inject constructor(
    private val renterRepository: RenterRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    //add renter
    fun addRenter(
        rentName: String,
        rentAmount: String,
        rentDate: String,
        rentServiceDate: String,
        rentContractTime: String,
        rentMembers: String,
        rentPlace: String,
        rentFloor: String,
        rentFlatType: String
    ) = viewModelScope.async {
        val newRenter = RenterEntity(
            id = "999",
            name = rentName,
            place = rentPlace,
            //desc = "Not energy consumption set up yet",
            createdAt = getPrettyCurrentDate(),
            contractTime = rentContractTime,
            members = rentMembers,
            floor = rentFloor,
            flatType = rentFlatType,
            rentPaymentDate = rentDate,
            servicesPaymentDate = rentServiceDate,
            rentAmount = rentAmount.toInt()
        )
//        Log.d("addRenter", "addRenter: $newRenter")
//        return true
        renterRepository.insertRenter(newRenter)
    }
}

fun getPrettyCurrentDate(): String {
    Util().apply {
        return "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
    }
}