package com.example.cobratelo_app.data.repo.pay_history

import android.util.Log
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.network.ServicePayHistoryEntity
import com.example.cobratelo_app.ui.ResponseUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServicePayHistoryRepositoryImpl @Inject constructor(
    private  val db: FirebaseFirestore
): ServicePayHistoryRepository {
    override fun getAllServicePayHistory(): List<ServicePayHistory> {
        return FakeSource.servicePayHistoryList.value!!
    }

    override fun getAllServicePayHistoryByRenterId(renterId: String): Flow<List<ServicePayHistoryEntity>> {
        return db.collection(RENTER_COLLECTION)
            .document(renterId)
            .collection(SERVICE_RENTER_COLLECTION)
            .orderBy(DATE_RENTER_FIELD, Query.Direction.ASCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "repoServiceHistory: ${snapshot.documents.size}")
                snapshot.toObjects(ServicePayHistoryEntity::class.java)
            }
            .catch {  }
    }

    override suspend fun updatePendingServices(selectedList: List<ServicePayHistoryEntity>, renterId: String): ResponseUI<Boolean> {
        return try {
            selectedList.forEach { item ->
                db.collection(RENTER_COLLECTION)
                    .document(renterId)
                    .collection(SERVICE_RENTER_COLLECTION)
                    .document(item.id)
                    .set(item)
                    .await()
            }
            ResponseUI.Success(true)
        } catch (e: Exception) {
            Log.d(TAG, "repoHistory error: ${e.message}")
            ResponseUI.Error("repoHistory error: ${e.message}")
        }
    }

    companion object {
        const val RENTER_COLLECTION = "renters"
        const val SERVICE_RENTER_COLLECTION = "services-pay"
        const val DATE_RENTER_FIELD = "date"
    }
}