package com.hooman.einkaufszettel.domain.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getAllBills():Flow<Resource<List<Bill>>>

    fun getBillById(id:Long):Flow<Resource<Bill>>

    suspend fun insertBill(bill: Bill):Flow<Resource<Unit>>

    suspend fun deleteBill(bill: Bill):Flow<Resource<Unit>>

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem):Flow<Resource<Unit>>

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem):Flow<Resource<Unit>>
}