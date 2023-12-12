package com.example.cobratelo_app.data.repo.consumption.personal

import android.util.Log
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RenterConsumption
import com.example.cobratelo_app.data.network.RenterConsumptionEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RenterConsumptionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : RenterConsumptionRepository {
    override suspend fun updateConsumption(
        newItem: RenterConsumptionEntity,
        renterId: String
    ): Boolean {
        return try {
            val newRef = db.collection("renters")
                .document(renterId)
                .collection(CONSUMPTION_COLLECTION)
            val newDoc = newRef.document()
            newDoc
                .set(newItem.copy(id = newDoc.id))
                .await()
            true
        } catch (e: Exception) {
            Log.d(TAG, "error in renter consumption repo while inserting a new: ${e.message}")
            false
        }
    }

    override fun getConsumptionByRenterId(renterId: Int): RenterConsumption {
        return FakeSource.renterConsumption.value?.first()!!
//        return FakeSource.renterConsumption.value?.first { it.renterId == renterId }!!
    }

    companion object {
        const val CONSUMPTION_COLLECTION = "consumptions"
    }
}