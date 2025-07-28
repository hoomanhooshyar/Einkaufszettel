package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.source.FirebaseService
import com.hooman.einkaufszettel.domain.source.FirebaseShoppingItemDataSource
import javax.inject.Inject

class FirebaseShoppingItemDataSourceImpl @Inject constructor(
    private val firebaseService: FirebaseService
):FirebaseShoppingItemDataSource {
    override suspend fun insertItem(shoppingItem: ShoppingItem) = firebaseService.insertShoppingItem(shoppingItem)

    override suspend fun getAllItemsByUserId(userId:String):List<ShoppingItem> = firebaseService.getAllShoppingItemsByUserId(userId)

    override suspend fun deleteShoppingItem(itemId:String) = firebaseService.deleteShoppingItem(itemId)

    override suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem> = firebaseService.getShoppingItemByBillId(billId)
}