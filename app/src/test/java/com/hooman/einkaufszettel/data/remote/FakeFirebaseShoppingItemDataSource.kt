package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.source.FirebaseShoppingItemDataSource

class FakeFirebaseShoppingItemDataSource:FirebaseShoppingItemDataSource {

    private val shoppingItemList = mutableListOf<ShoppingItem>()

    override suspend fun insertItem(shoppingItem: ShoppingItem) {
        shoppingItemList.removeIf { it.id == shoppingItem.id }
        shoppingItemList.add(shoppingItem)
    }

    override suspend fun getAllItemsByUserId(userId: String): List<ShoppingItem> {
        return shoppingItemList.filter { it.userId == userId }
    }

    override suspend fun deleteShoppingItem(itemId: String) {
        shoppingItemList.removeIf { it.id == itemId }
    }

    override suspend fun getShoppingItemByBillId(billId: Long): List<ShoppingItem> {
        return shoppingItemList.filter { it.billId == billId }
    }
}