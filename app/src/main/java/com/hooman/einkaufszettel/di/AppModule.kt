package com.hooman.einkaufszettel.di

import android.app.Application
import androidx.room.Room
import com.hooman.einkaufszettel.data.local.db.AppDatabase
import com.hooman.einkaufszettel.data.repository.AppRepositoryImpl
import com.hooman.einkaufszettel.domain.repository.AppRepository
import com.hooman.einkaufszettel.domain.usecase.DeleteBillUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteShoppingItemUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetBillByIdFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertShoppingItemToLocalUseCase
import com.hooman.einkaufszettel.presentation.util.internet_connection.NetworkConnectivityObserver
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
    fun provideAppRepository(
        db:AppDatabase
    ):AppRepository{
        return AppRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app:Application):AppDatabase{
        return Room
            .databaseBuilder(app,AppDatabase::class.java,"buy.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideGetAllBillsUseCase(repository: AppRepository):GetAllBillsFromLocalUseCase{
        return GetAllBillsFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBillByIdUseCase(repository: AppRepository):GetBillByIdFromLocalUseCase{
        return GetBillByIdFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertBillUseCase(repository: AppRepository):InsertBillToLocalUseCase{
        return InsertBillToLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteBillUseCase(repository: AppRepository):DeleteBillUseCase{
        return DeleteBillUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertShoppingItemUseCase(repository: AppRepository):InsertShoppingItemToLocalUseCase{
        return InsertShoppingItemToLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteShoppingItemUseCase(repository: AppRepository):DeleteShoppingItemUseCase{
        return DeleteShoppingItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(app:Application):NetworkConnectivityObserver{
        return NetworkConnectivityObserver(app)
    }



}