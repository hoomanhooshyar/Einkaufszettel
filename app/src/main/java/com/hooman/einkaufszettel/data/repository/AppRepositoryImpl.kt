package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.local.dao.AppDao
import com.hooman.einkaufszettel.data.mapper.toBill
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class AppRepositoryImpl(private val dao: AppDao) : AppRepository {
    override fun getAllBills(): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading())
        dao.getAllBills()
            .collect { list ->
                emit(Resource.Success(data = list.map { it.toBill() }))
            }
    }.catch { e ->
        emit(Resource.Error(message = e.message ?: "Unexpected Error"))
    }


    override fun getBillById(id: Long): Flow<Resource<Bill>> = flow<Resource<Bill>> {
        emit(Resource.Loading())
        dao.getBillById(id)
            .collect { billWithItems ->
                if (billWithItems != null)
                    emit(Resource.Success(data = billWithItems.toBill()))
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

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                dao.insertShoppingItem(shoppingItem.toShoppingItemEntity())
                emit(Resource.Success(data = Unit))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message!!))
            }
        }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                dao.deleteShoppingItem(shoppingItem.toShoppingItemEntity())
                emit(Resource.Success(data = Unit))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message!!))
            }
        }
}