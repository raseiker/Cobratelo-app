package com.example.cobratelo_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RenterRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    val renterList: LiveData<List<Renter>> = repository.getAllRenter()
}