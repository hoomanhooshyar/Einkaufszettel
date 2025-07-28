package com.hooman.einkaufszettel.data.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.data.remote.FirebaseProductDataSourceImpl
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.repository.FirebaseProductRepository
import com.hooman.einkaufszettel.domain.source.FirebaseProductDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseProductRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseProductDataSource
):FirebaseProductRepository {
    override suspend fun insertProduct(product: Product): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            if(product.name == ""){
                emit(Resource.Error(message = "Name shouldn't be empty"))
            }else if(product.userId == ""){
                emit(Resource.Error(message = "User id is empty"))
            }else if(product.price <= 0.0){
                emit(Resource.Error(message = "Price is not correct"))
            }else{
                dataSource.insertProduct(product)
                emit(Resource.Success(data = Unit))
            }
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAllProductsByUserId(userId:String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val products = dataSource.getAllProductsByUserId(userId)
            emit(Resource.Success(data = products))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getProductByName(name: String): Flow<Resource<List<Product>>> = flow{
        emit(Resource.Loading())
        try {
            val products = dataSource.getProductByName(name)
            emit(Resource.Success(data = products))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun getProductById(id: Long): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val product = dataSource.getProductById(id)
            emit(Resource.Success(data = product))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteProduct(id: Long): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            dataSource.deleteProduct(id)
            emit(Resource.Success(Unit))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}