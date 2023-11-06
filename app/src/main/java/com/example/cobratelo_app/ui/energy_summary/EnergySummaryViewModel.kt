package com.example.cobratelo_app.ui.energy_summary

import androidx.lifecycle.ViewModel
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.data.model.toEnergyConsumptionUI
import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepository
import com.example.cobratelo_app.ui.ResponseUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EnergySummaryViewModel @Inject constructor(
    private val energyConsumptionRepository: EnergyConsumptionRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
    private var _stateUI = MutableStateFlow<ResponseUI<EnergyConsumption>>(ResponseUI.Loading)
    val stateUI: StateFlow<ResponseUI<EnergyConsumption>> get() = _stateUI.asStateFlow()

    //get unique energy consumption
    fun getEnergyConsumption() {
        _stateUI.value = ResponseUI.Loading
        _stateUI.value = energyConsumptionRepository.getEnergyConsumption()

    }
}