package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Bill
import javax.inject.Inject

class FirebaseBillDataSource @Inject constructor(
    private val firebaseService: FirebaseService
) {

    suspend fun insertBill(bill: Bill) = firebaseService.insertBill(bill)

    suspend fun getAllBills():List<Bill> = firebaseService.getAllBills()

    suspend fun getBillById(billId: String):List<Bill> = firebaseService.getBillById(billId)

    suspend fun deleteBill(billId:String) = firebaseService.deleteBill(billId)
}