package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.local.dao.AppDao
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class AppRepositoryImpl(private val dao: AppDao) : AppRepository {
    override fun getAllBills(): Flow<Resource<List<Bill>>> =
        dao.getAllBills()
            .map { list -> Resource.Success(data = list.map { it.toBill() }) as Resource<List<Bill>> }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Error(message = e.message!!)) }


    override fun getBillById(id: Long): Flow<Resource<Bill>> =
        dao.getBillById(id)
            .map {
                if (it != null)
                    Resource.Success(data = it.toBill()) as Resource<Bill>
                else
                    Resource.Error(message = "Bill not found")
            }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Error(message = e.message!!)) }

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