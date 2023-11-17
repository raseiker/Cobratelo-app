package com.example.cobratelo_app.data.repo.renter

import androidx.lifecycle.LiveData
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.RenterEntity
import kotlinx.coroutines.flow.Flow

interface RenterRepository {

    fun getAllRenter(): Flow<List<RenterEntity>>

    fun getRenterById(renterId: String): Flow<RenterEntity?>

    fun getRenterByName(renterName: String): Renter

    fun insertRenter(renter: Renter): Boolean
}