package com.example.cobratelo_app.data.repo.consumption.general

import android.util.Log
import com.example.cobratelo_app.core.TAG
import com.example.cobratelo_app.data.local.FakeSource
import com.example.cobratelo_app.data.model.EnergyConsumption
import com.example.cobratelo_app.data.network.EnergyConsumptionEntity
import com.example.cobratelo_app.ui.ResponseUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EnergyConsumptionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): EnergyConsumptionRepository {
    override fun getEnergyConsumption(): Flow<ResponseUI<EnergyConsumptionEntity>> {
        return db.collection(ENERGY_CONSUMPTION_COLLECTION)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "energy repo snapshot size: ${snapshot.size()}")
                if (snapshot.size() == 0) {
                    ResponseUI.Error("You don't set an energy consumption yet. Do you want to set up now ?")
                } else {
                    val data = snapshot.last()
                        .toObject(EnergyConsumptionEntity::class.java)
                    ResponseUI.Success(data)
                }
            }
            .catch {
                ResponseUI.Error("An error was encountered: ${it.message}")
            }
    }

    override suspend fun insertEnergyConsumption(item: EnergyConsumptionEntity): Boolean {
        return try {
            val newRef = db.collection(ENERGY_CONSUMPTION_COLLECTION)
                .document()
            newRef
                .set(item.copy(id = newRef.id))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        const val ENERGY_CONSUMPTION_COLLECTION = "energy-consumption"
    }
}