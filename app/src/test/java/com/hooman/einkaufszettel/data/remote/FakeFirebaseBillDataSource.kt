package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.source.FirebaseBillDataSource

class FakeFirebaseBillDataSource:FirebaseBillDataSource {

    private val billList = mutableListOf<Bill>()

    override suspend fun insertBill(bill: Bill) {
        billList.removeIf { it.id == bill.id }
        billList.add(bill)
    }

    override suspend fun getAllBillsByUserId(userId: String): List<Bill> {
        return billList.filter { it.userId == userId }
    }

    override suspend fun getBillById(billId: String): List<Bill> {
        return billList.filter { it.id.toString() == billId }
    }

    override suspend fun deleteBill(billId: String) {
        billList.removeIf { it.id.toString() == billId }
    }
}