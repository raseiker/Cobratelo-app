package com.example.cobratelo_app.di

import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepository
import com.example.cobratelo_app.data.repo.consumption.general.EnergyConsumptionRepositoryImpl
import com.example.cobratelo_app.data.repo.consumption.personal.RenterConsumptionRepository
import com.example.cobratelo_app.data.repo.consumption.personal.RenterConsumptionRepositoryImpl
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepository
import com.example.cobratelo_app.data.repo.pay_history.RentPayHistoryRepositoryImpl
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepository
import com.example.cobratelo_app.data.repo.pay_history.ServicePayHistoryRepositoryImpl
import com.example.cobratelo_app.data.repo.renter.RenterRepository
import com.example.cobratelo_app.data.repo.renter.RenterFakeRepositoryImpl
import com.example.cobratelo_app.data.repo.renter.RenterRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideRenterRepository(
        db: FirebaseFirestore
    ): RenterRepository {
        return RenterRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideRentPayHistoryRepository(
        db: FirebaseFirestore
    ): RentPayHistoryRepository{
        return RentPayHistoryRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideServicePayHistoryRepository(
        db: FirebaseFirestore
    ): ServicePayHistoryRepository{
        return ServicePayHistoryRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideRenterConsumptionRepository(

    ): RenterConsumptionRepository {
        return RenterConsumptionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideEnergyConsumption(

    ): EnergyConsumptionRepository {
        return EnergyConsumptionRepositoryImpl()
    }
}