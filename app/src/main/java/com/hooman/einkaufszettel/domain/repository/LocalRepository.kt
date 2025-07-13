package com.hooman.einkaufszettel.domain.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getAllBills():Flow<Resource<List<Bill>>>

    fun getBillById(id:Long):Flow<Resource<Bill>>

    suspend fun insertBill(bill: Bill):Flow<Resource<Unit>>

    suspend fun deleteBill(bill: Bill):Flow<Resource<Unit>>

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem,billId: Long):Flow<Resource<Unit>>

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem,billId:Long):Flow<Resource<Unit>>


    fun getAllProducts():Flow<Resource<List<Product>>>

    fun getProductByName(name:String):Flow<Resource<Product>>

    fun getProductById(id:Long):Flow<Resource<Product>>

    suspend fun insertProduct(product: Product):Flow<Resource<Unit>>

    suspend fun deleteProduct(product: Product):Flow<Resource<Unit>>
}