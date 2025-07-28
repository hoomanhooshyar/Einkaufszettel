package com.hooman.einkaufszettel.domain.source

import com.hooman.einkaufszettel.domain.model.Bill

interface FirebaseBillDataSource {
    suspend fun insertBill(bill: Bill)
    suspend fun getAllBillsByUserId(userId:String):List<Bill>
    suspend fun getBillById(billId: String):List<Bill>
    suspend fun deleteBill(billId:String)
}