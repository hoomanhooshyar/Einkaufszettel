package com.hooman.einkaufszettel.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.hooman.einkaufszettel.data.local.db.AppDatabase
import com.hooman.einkaufszettel.data.remote.FirebaseBillDataSource
import com.hooman.einkaufszettel.data.remote.FirebaseService
import com.hooman.einkaufszettel.data.remote.FirebaseShoppingItemDataSource
import com.hooman.einkaufszettel.data.repository.FirebaseBillRepositoryImpl
import com.hooman.einkaufszettel.data.repository.FirebaseShoppingItemRepositoryImpl
import com.hooman.einkaufszettel.data.repository.LocalRepositoryImpl
import com.hooman.einkaufszettel.domain.repository.FirebaseBillRepository
import com.hooman.einkaufszettel.domain.repository.FirebaseShoppingItemRepository
import com.hooman.einkaufszettel.domain.repository.LocalRepository
import com.hooman.einkaufszettel.domain.usecase.DeleteBillFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteBillFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteShoppingItemFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteShoppingItemFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllShoppingItemsFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetBillByIdFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetBillByIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetShoppingItemByBillIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertShoppingItemToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertShoppingItemToRemoteUseCase
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
    ):LocalRepository{
        return LocalRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideFirebaseBillRepository(
        dataSource:FirebaseBillDataSource
    ):FirebaseBillRepository = FirebaseBillRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideFirebaseShoppingItemRepository(
        dataSource: FirebaseShoppingItemDataSource
    ):FirebaseShoppingItemRepository = FirebaseShoppingItemRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideAppDatabase(app:Application):AppDatabase{
        return Room
            .databaseBuilder(app,AppDatabase::class.java,"buy.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideGetAllBillsUseCase(repository: LocalRepository):GetAllBillsFromLocalUseCase{
        return GetAllBillsFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBillByIdUseCase(repository: LocalRepository):GetBillByIdFromLocalUseCase{
        return GetBillByIdFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertBillUseCase(repository: LocalRepository):InsertBillToLocalUseCase{
        return InsertBillToLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteBillUseCase(repository: LocalRepository):DeleteBillFromLocalUseCase{
        return DeleteBillFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertShoppingItemUseCase(repository: LocalRepository):InsertShoppingItemToLocalUseCase{
        return InsertShoppingItemToLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteShoppingItemUseCase(repository: LocalRepository):DeleteShoppingItemFromLocalUseCase{
        return DeleteShoppingItemFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteBillFromRemoteUseCase(repository: FirebaseBillRepository):DeleteBillFromRemoteUseCase{
        return DeleteBillFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteShoppingItemFromRemoteUseCase(repository: FirebaseShoppingItemRepository):DeleteShoppingItemFromRemoteUseCase{
        return DeleteShoppingItemFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllBillsFromRemoteUseCase(repository: FirebaseBillRepository):GetAllBillsFromRemoteUseCase{
        return GetAllBillsFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBillByIdFromRemote(repository: FirebaseBillRepository):GetBillByIdFromRemoteUseCase{
        return GetBillByIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllShoppingItemFromRemoteUseCase(repository: FirebaseShoppingItemRepository):GetAllShoppingItemsFromRemoteUseCase{
        return GetAllShoppingItemsFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetShoppingItemByBillIdFromRemoteUseCase(repository: FirebaseShoppingItemRepository):GetShoppingItemByBillIdFromRemoteUseCase{
        return GetShoppingItemByBillIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertBillToRemoteUseCase(repository: FirebaseBillRepository):InsertBillToRemoteUseCase{
        return InsertBillToRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertShoppingItemToRemoteUseCase(repository: FirebaseShoppingItemRepository):InsertShoppingItemToRemoteUseCase{
        return InsertShoppingItemToRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(app:Application):NetworkConnectivityObserver{
        return NetworkConnectivityObserver(app)
    }

    @Provides
    @Singleton
    fun provideFireStore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseService(
        firestore: FirebaseFirestore
    ):FirebaseService = FirebaseService(firestore)

    @Provides
    @Singleton
    fun provideFirebaseBillDataSource(
        service: FirebaseService
    ):FirebaseBillDataSource = FirebaseBillDataSource(service)

    @Provides
    @Singleton
    fun provideFirebaseShoppingItemDataSource(
        service: FirebaseService
    ):FirebaseShoppingItemDataSource = FirebaseShoppingItemDataSource(service)





}