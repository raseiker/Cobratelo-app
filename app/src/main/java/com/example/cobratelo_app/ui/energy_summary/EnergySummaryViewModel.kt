package com.example.cobratelo_app.ui.energy_summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.data.model.toEnergyConsumptionUI
import com.example.cobratelo_app.data.network.EnergyConsumptionEntity
import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepository
import com.example.cobratelo_app.ui.ResponseUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnergySummaryViewModel @Inject constructor(
    private val energyConsumptionRepository: EnergyConsumptionRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    private var _stateUI = MutableStateFlow<ResponseUI<EnergyConsumptionEntity>>(ResponseUI.Loading)
    val stateUI: StateFlow<ResponseUI<EnergyConsumptionEntity>> get() = energyConsumptionRepository.getEnergyConsumption()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), ResponseUI.Loading)

    //get unique energy consumption
    fun getEnergyConsumption() = viewModelScope.launch{
        _stateUI.value = ResponseUI.Loading
        _stateUI.value = energyConsumptionRepository.getEnergyConsumption()
            .first()
    }
}