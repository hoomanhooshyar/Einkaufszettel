package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.local.dao.AppDao
import com.hooman.einkaufszettel.data.mapper.toDomain
import com.hooman.einkaufszettel.data.mapper.toProduct
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LocalRepositoryImpl(private val dao: AppDao) : LocalRepository {
    override fun getAllBills(): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading())
        dao.getAllBills()
            .collect { list ->
                emit(Resource.Success(data = list.map { it.toDomain() }))
            }
    }.catch { e ->
        emit(Resource.Error(message = e.message ?: "Unexpected Error"))
    }


    override fun getBillById(id: Long): Flow<Resource<Bill>> = flow<Resource<Bill>> {
        emit(Resource.Loading())
        dao.getBillById(id)
            .collect { billWithItems ->
                if (billWithItems != null)
                    emit(Resource.Success(data = billWithItems.toDomain()))
                else
                    emit(Resource.Error(message = "Not Found"))

            }
    }.catch { e ->
        emit(Resource.Error(message = e.message ?: "Unexpected Error"))
    }


    override suspend fun insertBill(bill: Bill): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dao.insertBill(bill.toBillEntity())
            emit(Resource.Success(data = Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message!!))
        }

    }

    override suspend fun deleteBill(bill: Bill): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            dao.deleteBill(bill.toBillEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message!!))
        }
    }

    override suspend fun insertShoppingItem(
        shoppingItem: ShoppingItem,
        billId: Long
    ): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                dao.insertShoppingItem(shoppingItem.toShoppingItemEntity())
                emit(Resource.Success(data = Unit))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message!!))
            }
        }

    override suspend fun deleteShoppingItem(
        shoppingItem: ShoppingItem,
        billId: Long
    ): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                dao.deleteShoppingItem(shoppingItem.toShoppingItemEntity())
                emit(Resource.Success(data = Unit))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message!!))
            }
        }


    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            dao.getAllProducts().collect { products ->
                emit(Resource.Success(data = products.map { it.toProduct() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }

    }

    override fun getProductByName(name: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            dao.getProductByName(name).collect { product ->

                emit(Resource.Success(data = product.toProduct()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override fun getProductById(id: Long): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            dao.getProductById(id).collect { product ->
                if (product != null)
                    emit(Resource.Success(data = product.toProduct()))
                else
                    emit(Resource.Error(message = "Product not Found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun insertProduct(product: Product):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dao.insertProduct(product.toProductEntity())
            emit(Resource.Success(data = Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteProduct(product: Product):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dao.deleteProduct(product.toProductEntity())
            emit(Resource.Success(data = null))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}