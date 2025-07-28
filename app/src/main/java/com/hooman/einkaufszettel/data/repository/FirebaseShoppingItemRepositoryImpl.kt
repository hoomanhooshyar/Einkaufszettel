package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FirebaseShoppingItemDataSourceImpl
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.FirebaseShoppingItemRepository
import com.hooman.einkaufszettel.domain.source.FirebaseShoppingItemDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseShoppingItemRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseShoppingItemDataSource
) : FirebaseShoppingItemRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                if (shoppingItem.productName == "" || shoppingItem.productName.isEmpty()) {
                    emit(Resource.Error(message = "Product name is empty"))
                    return@flow
                } else if (shoppingItem.itemCount <= 0) {
                    emit(Resource.Error(message = "Item count should bigger than 0"))
                    return@flow
                } else if (shoppingItem.productPrice <= 0.0) {
                    emit(Resource.Error(message = "Product price should be bigger than 0.0"))
                    return@flow
                }else if(shoppingItem.userId.isEmpty() || shoppingItem.userId == ""){
                    emit(Resource.Error(message = "Invalid user id"))
                    return@flow
                }
                dataSource.insertItem(shoppingItem)
                emit(Resource.Success(Unit))

            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }

    override suspend fun getAllShoppingItemsByUserId(userId: String): Flow<Resource<List<ShoppingItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val items = dataSource.getAllItemsByUserId(userId)
                emit(Resource.Success(items))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }

    override suspend fun deleteShoppingItem(itemId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dataSource.deleteShoppingItem(itemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getShoppingItemByBillId(billId: Long): Flow<Resource<List<ShoppingItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val items = dataSource.getShoppingItemByBillId(billId)
                emit(Resource.Success(data = items))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
}