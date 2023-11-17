package com.example.cobratelo_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.toRenter
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RenterRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    val renterList: LiveData<List<Renter>> = repository.getAllRenter()
        .map { it.map { item -> item.toRenter() } }
        .asLiveData()
}