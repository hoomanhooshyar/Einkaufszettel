package com.hooman.einkaufszettel.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.hooman.einkaufszettel.data.local.db.AppDatabase
import com.hooman.einkaufszettel.data.remote.FirebaseBillDataSourceImpl
import com.hooman.einkaufszettel.data.remote.FirebaseProductDataSourceImpl
import com.hooman.einkaufszettel.domain.source.FirebaseService
import com.hooman.einkaufszettel.data.remote.FirebaseServiceImpl
import com.hooman.einkaufszettel.data.remote.FirebaseShoppingItemDataSourceImpl
import com.hooman.einkaufszettel.data.repository.FirebaseBillRepositoryImpl
import com.hooman.einkaufszettel.data.repository.FirebaseShoppingItemRepositoryImpl
import com.hooman.einkaufszettel.data.repository.LocalRepositoryImpl
import com.hooman.einkaufszettel.domain.repository.FirebaseBillRepository
import com.hooman.einkaufszettel.domain.repository.FirebaseProductRepository
import com.hooman.einkaufszettel.domain.repository.FirebaseShoppingItemRepository
import com.hooman.einkaufszettel.domain.repository.LocalRepository
import com.hooman.einkaufszettel.domain.source.FirebaseBillDataSource
import com.hooman.einkaufszettel.domain.source.FirebaseProductDataSource
import com.hooman.einkaufszettel.domain.source.FirebaseShoppingItemDataSource
import com.hooman.einkaufszettel.domain.usecase.DeleteBillFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteBillFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteProductFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteProductFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteShoppingItemFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.DeleteShoppingItemFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllBillsByUserIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllProductsFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllProductsByUserIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetAllShoppingItemsByUserIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetBillByIdFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetBillByIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetProductByIdFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetProductByIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetProductByNameFromLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.GetProductByNameFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.GetShoppingItemByBillIdFromRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertBillToRemoteUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertProductToLocalUseCase
import com.hooman.einkaufszettel.domain.usecase.InsertProductToRemoteUseCase
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
        dataSource:FirebaseBillDataSourceImpl
    ):FirebaseBillRepository = FirebaseBillRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideFirebaseShoppingItemRepository(
        dataSource: FirebaseShoppingItemDataSourceImpl
    ):FirebaseShoppingItemRepository = FirebaseShoppingItemRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideAppDatabase(app:Application):AppDatabase{
        return Room
            .databaseBuilder(app,AppDatabase::class.java,"buy.db")
            .build()
    }

    /*****Use cases*****/
    //Local use cases
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
    fun provideGetAllProductsUseCase(repository: LocalRepository):GetAllProductsFromLocalUseCase{
        return GetAllProductsFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByNameUseCase(repository: LocalRepository):GetProductByNameFromLocalUseCase{
        return GetProductByNameFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(repository: LocalRepository):GetProductByIdFromLocalUseCase{
        return GetProductByIdFromLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertProductUseCase(repository: LocalRepository):InsertProductToLocalUseCase{
        return InsertProductToLocalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteProductUseCase(repository: LocalRepository):DeleteProductFromLocalUseCase{
        return DeleteProductFromLocalUseCase(repository)
    }

    //Remote use cases

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
    fun provideGetAllBillsByUserIdFromRemoteUseCase(repository: FirebaseBillRepository):GetAllBillsByUserIdFromRemoteUseCase{
        return GetAllBillsByUserIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBillByIdFromRemote(repository: FirebaseBillRepository):GetBillByIdFromRemoteUseCase{
        return GetBillByIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllShoppingItemByUserIdFromRemoteUseCase(repository: FirebaseShoppingItemRepository):GetAllShoppingItemsByUserIdFromRemoteUseCase{
        return GetAllShoppingItemsByUserIdFromRemoteUseCase(repository)
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
    fun provideInsertProductToRemoteUseCase(repository: FirebaseProductRepository):InsertProductToRemoteUseCase{
        return InsertProductToRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllProductsByUserIdFromRemoteUseCase(repository: FirebaseProductRepository):GetAllProductsByUserIdFromRemoteUseCase{
        return GetAllProductsByUserIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByNameFromRemoteUseCase(repository: FirebaseProductRepository):GetProductByNameFromRemoteUseCase{
        return GetProductByNameFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdFromRemoteUseCase(repository: FirebaseProductRepository):GetProductByIdFromRemoteUseCase{
        return GetProductByIdFromRemoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteProductFromRemoteUseCase(repository: FirebaseProductRepository):DeleteProductFromRemoteUseCase{
        return DeleteProductFromRemoteUseCase(repository)
    }
    /*******End of use cases******/



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
    ): FirebaseService = FirebaseServiceImpl(firestore)

    @Provides
    @Singleton
    fun provideFirebaseBillDataSource(
        service: FirebaseService
    ):FirebaseBillDataSource = FirebaseBillDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideFirebaseShoppingItemDataSource(
        service: FirebaseService
    ):FirebaseShoppingItemDataSource = FirebaseShoppingItemDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideFirebaseProductDataSource(
        service: FirebaseService
    ):FirebaseProductDataSource = FirebaseProductDataSourceImpl(service)





}