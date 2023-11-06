package com.example.cobratelo_app.data.repo.renter

import androidx.lifecycle.LiveData
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.Renter

class RenterRepositoryImpl: RenterRepository {
    override fun getAllRenter(): LiveData<List<Renter>> {
        return FakeSource.renterList
    }

    override fun getRenterById(renterId: Int): Renter {
        return FakeSource.renterList.value?.first { it.id == renterId }!!
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