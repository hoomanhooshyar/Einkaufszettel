package com.hooman.einkaufszettel.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val billsCollection = firestore.collection("bills")
    private val itemsCollection = firestore.collection("shoppingItems")

    suspend fun insertBill(bill: Bill){
        billsCollection.document(bill.id.toString()).set(bill).await()
    }

    suspend fun getAllBills():List<Bill>{
        return billsCollection.get().await().toObjects(Bill::class.java)
    }

    suspend fun deleteBill(billId:String){
        billsCollection.document(billId).delete().await()
    }

    suspend fun getBillById(billId:String):List<Bill>{
        return try{
            billsCollection
                .whereEqualTo("id",billId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Bill::class.java) }
        }catch (e:Exception){
            emptyList()
        }
    }

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem){
        itemsCollection.document(shoppingItem.id).set(shoppingItem).await()
    }

    suspend fun getAllShoppingItems():List<ShoppingItem>{
        return itemsCollection.get().await().toObjects(ShoppingItem::class.java)
    }

    suspend fun deleteShoppingItem(itemId:String){
        itemsCollection.document(itemId).delete().await()
    }

    suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem>{
        return try {
            itemsCollection
                .whereEqualTo("billId",billId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(ShoppingItem::class.java) }
        }catch (e:Exception){
            emptyList()
        }
    }

}