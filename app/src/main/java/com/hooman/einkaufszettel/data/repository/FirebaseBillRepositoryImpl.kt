package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FirebaseBillDataSourceImpl
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.repository.FirebaseBillRepository
import com.hooman.einkaufszettel.domain.source.FirebaseBillDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseBillRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseBillDataSource
):FirebaseBillRepository {
    override suspend fun insertBill(bill: Bill):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try{

            dataSource.insertBill(bill)
            emit(Resource.Success(Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAllBillsByUserId(userId:String): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading())
        try {
            val bills = dataSource.getAllBillsByUserId(userId)
            emit(Resource.Success(bills))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getBillById(billId: String): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading())
        try {
            val bills = dataSource.getBillById(billId)
            emit(Resource.Success(bills))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteBill(billId: String):Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            dataSource.deleteBill(billId)
            emit(Resource.Success(Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}