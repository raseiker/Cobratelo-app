package com.example.cobratelo_app.data.repo.renter

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.RenterEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class RenterFakeRepositoryImpl(): RenterRepository {
    override fun getAllRenter(): Flow<List<RenterEntity>> {
        return flowOf(listOf())
//        return FakeSource.renterList.asFlow()
    }

    override fun getRenterById(renterId: String): Flow<RenterEntity?> {
        return flowOf()
//        return FakeSource.renterList.value?.first { it.id == renterId }!!
    }

    override fun getRenterByName(renterName: String): Renter {
        return FakeSource.renterList.value?.first { it.name == renterName }!!
    }

    override fun insertRenter(renter: Renter): Boolean {
        return try {
            FakeSource.renterList.value = FakeSource.renterList.value?.plus(renter)
            true
        } catch (e: Exception) {
            false
        }
    }
}