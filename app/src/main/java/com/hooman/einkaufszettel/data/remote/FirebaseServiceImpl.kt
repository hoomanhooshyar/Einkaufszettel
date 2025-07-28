package com.hooman.einkaufszettel.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.source.FirebaseService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FirebaseService {

    private val billsCollection = firestore.collection("bills")
    private val itemsCollection = firestore.collection("shoppingItems")
    private val productCollection = firestore.collection("products")

    override suspend fun insertBill(bill: Bill){
        billsCollection.document(bill.id.toString()).set(bill).await()
    }

    override suspend fun getAllBillsByUseId(userId:String):List<Bill>{
        return billsCollection
            .whereEqualTo("userId",userId)
            .get()
            .await()
            .toObjects(Bill::class.java)
    }

    override suspend fun deleteBill(billId:String){
        billsCollection.document(billId).delete().await()
    }

    override suspend fun getBillById(billId:String):List<Bill>{
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

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem){
        itemsCollection.document(shoppingItem.id).set(shoppingItem).await()
    }

    override suspend fun getAllShoppingItemsByUserId(userId:String):List<ShoppingItem>{
        return itemsCollection
            .whereEqualTo("userId",userId)
            .get().await().toObjects(ShoppingItem::class.java)
    }

    override suspend fun deleteShoppingItem(itemId:String){
        itemsCollection.document(itemId).delete().await()
    }

    override suspend fun getShoppingItemByBillId(billId:Long):List<ShoppingItem>{
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

    override suspend fun insertProduct(product: Product){
        productCollection.document(product.id.toString()).set(product).await()
    }

    override suspend fun getAllProductsByUserId(userId: String):List<Product>{
        return productCollection
            .whereEqualTo("userId",userId)
            .get()
            .await()
            .toObjects(Product::class.java)
    }

    override suspend fun getProductByName(name:String):List<Product>{
        return try {
            productCollection
                .whereEqualTo("name",name)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
        }catch (e:Exception){
            emptyList()
        }
    }

    override suspend fun getProductById(id:Long):List<Product>{
        return try {
            productCollection
                .whereEqualTo("id",id)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
        }catch (e:Exception){
            emptyList()
        }
    }

    override suspend fun deleteProduct(id:Long){
        productCollection.document(id.toString()).delete().await()
    }

}