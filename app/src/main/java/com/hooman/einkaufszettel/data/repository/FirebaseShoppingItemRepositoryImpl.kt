package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FirebaseShoppingItemDataSource
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.FirebaseShoppingItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseShoppingItemRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseShoppingItemDataSource
):FirebaseShoppingItemRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dataSource.insertItem(shoppingItem)
            emit(Resource.Success(Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAllShoppingItems(): Flow<Resource<List<ShoppingItem>>> = flow {
        emit(Resource.Loading())
        try {
            val items = dataSource.getAllItems()
            emit(Resource.Success(items))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteShoppingItem(itemId: String):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dataSource.deleteShoppingItem(itemId)
            emit(Resource.Success(Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getShoppingItemByBillId(billId: Long): Flow<Resource<List<ShoppingItem>>> = flow {
        emit(Resource.Loading())
        try {
            val items = dataSource.getShoppingItemByBillId(billId)
            emit(Resource.Success(data = items))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}