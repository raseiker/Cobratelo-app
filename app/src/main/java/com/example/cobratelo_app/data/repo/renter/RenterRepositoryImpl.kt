package com.example.cobratelo_app.data.repo.renter

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.network.RenterEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RenterRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): RenterRepository {
    override fun getAllRenter(): Flow<List<RenterEntity>> {
        return db.collection(RENTER_COLLECTION)
            .orderBy(RENTER_NAME_FIELD, Query.Direction.ASCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "repoRenter: ${snapshot.documents.size}")
                snapshot.toObjects(RenterEntity::class.java)
            }
            .catch { Log.d(TAG, "repoRenter error: ${it.message}") }
    }

    override fun getRenterById(renterId: String): Flow<RenterEntity?> {
        return db.collection("renters")
            .document(renterId)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "repoRenter by id: ${snapshot.data}")
                snapshot.toObject(RenterEntity::class.java)
            }
            .catch { Log.d(TAG, "repoRenter by id error: ${it.message}")  }
    }

    override fun getRenterByName(renterName: String): Renter {
        TODO("Not yet implemented")
    }

    override suspend fun insertRenter(renter: RenterEntity): Boolean {
        return try {
            val newDocument = db.collection(RENTER_COLLECTION)
                .document()
            newDocument
                .set(renter.copy(id = newDocument.id))
                .await()
            true
        } catch (e: Exception) {
            Log.d(TAG, "repoRenter by id: ${e.message}")
            false
        }

    }

    companion object {
        const val RENTER_COLLECTION = "renters"
        const val RENTER_NAME_FIELD = "name"
    }
}