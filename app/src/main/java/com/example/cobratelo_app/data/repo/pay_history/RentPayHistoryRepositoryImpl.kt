package com.example.cobratelo_app.data.repo.pay_history

import android.util.Log
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.RentPayHistory
import com.example.cobratelo_app.data.model.getMonthValue
import com.example.cobratelo_app.data.network.RentPayHistoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RentPayHistoryRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): RentPayHistoryRepository {
    override fun getAllRentPayHistory(): List<RentPayHistory> {
        return FakeSource.rentPayHistoryList.value!!
    }

    override fun getRentPayHistoryByRenterId(renterId: String): Flow<List<RentPayHistoryEntity>> {
        return db.collection(RENTER_COLLECTION)
            .document(renterId)
            .collection(RENT_RENTER_COLLECTION)
            .orderBy(DATE_RENTER_FIELD, Query.Direction.ASCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "repoHistory: ${snapshot.documents.size}")

                snapshot.toObjects(RentPayHistoryEntity::class.java)
            }
            .catch {
                Log.d(TAG, "repoHistory error: ${it.message}")
            }

    }

    override fun getLastCanceledPayment(): RentPayHistory? {
        return FakeSource.rentPayHistoryList.value
            ?.filter { it.status }
            ?.maxBy { it.getMonthValue() }
    }

    companion object {
        const val RENTER_COLLECTION = "renters"
        const val RENT_RENTER_COLLECTION = "rents-pay"
        const val DATE_RENTER_FIELD = "date"
    }
}