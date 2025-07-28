package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.source.FirebaseBillDataSource
import com.hooman.einkaufszettel.domain.source.FirebaseService
import javax.inject.Inject

class FirebaseBillDataSourceImpl @Inject constructor(
    private val firebaseService: FirebaseService
):FirebaseBillDataSource {

    override suspend fun insertBill(bill: Bill) = firebaseService.insertBill(bill)

    override suspend fun getAllBillsByUserId(userId:String):List<Bill> = firebaseService.getAllBillsByUseId(userId)

    override suspend fun getBillById(billId: String):List<Bill> = firebaseService.getBillById(billId)

    override suspend fun deleteBill(billId:String) = firebaseService.deleteBill(billId)
}