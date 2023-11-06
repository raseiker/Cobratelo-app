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
import com.example.cobratelo_app.data.repo.renter.RenterRepositoryImpl
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
    fun provideRenterRepository(

    ): RenterRepository {
        return RenterRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRentPayHistoryRepository(

    ): RentPayHistoryRepository{
        return RentPayHistoryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideServicePayHistoryRepository(

    ): ServicePayHistoryRepository{
        return ServicePayHistoryRepositoryImpl()
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