package com.example.cobratelo_app.data.repo.renter

import androidx.lifecycle.LiveData
import com.example.cobratelo_app.data.model.Renter

interface RenterRepository {

    fun getAllRenter(): LiveData<List<Renter>>

    fun getRenterById(renterId: Int): Renter

    fun getRenterByName(renterName: String): Renter

    fun insertRenter(renter: Renter): Boolean
}