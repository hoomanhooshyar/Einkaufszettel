package com.hooman.einkaufszettel.data.local.dao

import com.hooman.einkaufszettel.data.local.entity.BillEntity
import com.hooman.einkaufszettel.data.local.entity.ProductEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import com.hooman.einkaufszettel.data.local.relation.BillWithItemsAndProducts
import com.hooman.einkaufszettel.data.local.relation.ShoppingItemWithProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeAppDao:AppDao {

    private val bills = mutableListOf<BillEntity>()
    private val shoppingItems = mutableListOf<ShoppingItemEntity>()
    private val products = mutableListOf<ProductEntity>()

    private val billFlow = MutableStateFlow<List<BillEntity>>(emptyList())
    private val productFlow = MutableStateFlow<List<ProductEntity>>(emptyList())

    override suspend fun insertBill(bill: BillEntity) {
        bills.removeAll { it.id == bill.id }
        bills.add(bill)
        billFlow.value = bills.toList()
    }

    override suspend fun deleteBill(bill: BillEntity) {
        bills.removeIf { it.id == bill.id }
        billFlow.value = bills.toList()
        shoppingItems.removeIf { it.billId == bill.id }
    }

    override fun getAllBills(): Flow<List<BillWithItemsAndProducts>> = flow {
        emit(bills.map { bill ->
            val items = shoppingItems.filter { it.billId == bill.id }
            val itemsWithProducts = items.mapNotNull { item ->
                val product = products.find { it.id == item.productId }
                product?.let {
                    ShoppingItemWithProduct(item,it)
                }
            }
            BillWithItemsAndProducts(bill,itemsWithProducts)
        })

    }

    override fun getBillById(id: Long): Flow<BillWithItemsAndProducts?> = flow {
        val bill = bills.find { it.id == id }
        if(bill != null){
            val items = shoppingItems.filter { it.billId == bill.id }
            val itemsWithProducts = items.mapNotNull { item ->
                val product = products.find { it.id == item.productId }
                product?.let {
                    ShoppingItemWithProduct(item,it)
                }
            }
            emit(BillWithItemsAndProducts(bill,itemsWithProducts))
        }else{
            emit(null)
        }
    }

    override suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingItems.removeIf { it.id == shoppingItemEntity.id }
        shoppingItems.add(shoppingItemEntity)
    }

    override suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        shoppingItems.removeIf { it.id == shoppingItemEntity.id }
    }

    override suspend fun insertProduct(productEntity: ProductEntity) {
        products.removeAll { it.id == productEntity.id }
        products.add(productEntity)
        productFlow.value = products.toList()
    }

    override fun getProductById(id: Long): Flow<ProductEntity?> = flow {
        emit(products.find { it.id == id })
    }

    override fun getAllProducts(): Flow<List<ProductEntity>> = productFlow

    override fun getProductByName(name: String): Flow<ProductEntity> = flow {
        val found = products.find { it.name == name }
        if(found != null) emit(found)
    }

    override suspend fun deleteProduct(productEntity: ProductEntity) {
        products.removeIf { it.id == productEntity.id }
        productFlow.value = products.toList()
    }
}