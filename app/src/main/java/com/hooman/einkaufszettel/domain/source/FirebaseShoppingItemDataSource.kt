package com.hooman.einkaufszettel.domain.source

import com.hooman.einkaufszettel.domain.model.ShoppingItem

interface FirebaseShoppingItemDataSource {
    suspend fun insertItem(shoppingItem: ShoppingItem)
    suspend fun getAllItemsByUserId(userId:String):List<ShoppingItem>
    suspend fun deleteShoppingItem(itemId:String)
    suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem>
}