package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.ShoppingItem
import javax.inject.Inject

class FirebaseShoppingItemDataSource @Inject constructor(
    private val firebaseService: FirebaseService
) {
    suspend fun insertItem(shoppingItem: ShoppingItem) = firebaseService.insertShoppingItem(shoppingItem)

    suspend fun getAllItems():List<ShoppingItem> = firebaseService.getAllShoppingItems()

    suspend fun deleteShoppingItem(itemId:String) = firebaseService.deleteShoppingItem(itemId)

    suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem> = firebaseService.getShoppingItemByBillId(billId)
}