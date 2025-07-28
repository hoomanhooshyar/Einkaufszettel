package com.hooman.einkaufszettel.domain.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import kotlinx.coroutines.flow.Flow

interface FirebaseBillRepository {
    suspend fun insertBill(bill: Bill):Flow<Resource<Unit>>

    suspend fun getAllBillsByUserId(userId:String):Flow<Resource<List<Bill>>>

    suspend fun getBillById(billId:String):Flow<Resource<List<Bill>>>

    suspend fun deleteBill(billId:String):Flow<Resource<Unit>>
}