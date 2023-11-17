package com.example.cobratelo_app.data.repo.pay_history

import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.ServicePayHistory
import com.example.cobratelo_app.data.network.ServicePayHistoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
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
                snapshot.toObjects(ServicePayHistoryEntity::class.java)
            }
            .catch {  }
    }

    companion object {
        const val RENTER_COLLECTION = "renters"
        const val SERVICE_RENTER_COLLECTION = "services-pay"
        const val DATE_RENTER_FIELD = "date"
    }
}