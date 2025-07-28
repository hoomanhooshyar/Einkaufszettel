package com.hooman.einkaufszettel.domain.source

import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.model.ShoppingItem

interface FirebaseService {
    suspend fun insertBill(bill:Bill)
    suspend fun getAllBillsByUseId(userId:String):List<Bill>
    suspend fun deleteBill(billId:String)
    suspend fun getBillById(billId:String):List<Bill>
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun getAllShoppingItemsByUserId(userId:String):List<ShoppingItem>
    suspend fun deleteShoppingItem(itemId:String)
    suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem>
    suspend fun insertProduct(product: Product)
    suspend fun getAllProductsByUserId(userId: String):List<Product>
    suspend fun getProductByName(name:String):List<Product>
    suspend fun getProductById(id:Long):List<Product>
    suspend fun deleteProduct(id:Long)
}