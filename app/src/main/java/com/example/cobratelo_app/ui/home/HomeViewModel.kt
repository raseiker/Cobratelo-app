package com.example.cobratelo_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.toRenter
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val renterRepository: RenterRepository,
    private val rentRepository: RentPayHistoryRepository,
    private val servicesRepository: ServicePayHistoryRepository
) : ViewModel() {
    // TODO: Combine renter repository, rentRepository and servicesRepository into renterList

    val renterList: LiveData<List<Renter>> = renterRepository.getAllRenter()
        .map { response ->
            response.map { item ->
                //check renter status
                val isRentUpToDate = servicesRepository.getAllServicePayHistoryByRenterId(item.id)
                    .firstOrNull()
                    ?.any { !it.status }
                    ?: rentRepository.getRentPayHistoryByRenterId(item.id)
                        .firstOrNull()
                        ?.any { !it.status }
                    ?: false //if there arent pending services and rent, then isRentUpToDate is false

                //check renter description
                item.toRenter(
                    status = !isRentUpToDate,//revert value because isRentUpDate doesn't represent renter status
                    desc = ""
                )
            }
        }
        .asLiveData()
}