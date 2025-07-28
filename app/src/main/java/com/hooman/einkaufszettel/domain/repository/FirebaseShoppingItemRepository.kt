package com.hooman.einkaufszettel.domain.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface FirebaseShoppingItemRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem):Flow<Resource<Unit>>

    suspend fun getAllShoppingItemsByUserId(userId:String):Flow<Resource<List<ShoppingItem>>>

    suspend fun deleteShoppingItem(itemId:String):Flow<Resource<Unit>>

    suspend fun getShoppingItemByBillId(billId:Long) : Flow<Resource<List<ShoppingItem>>>
}