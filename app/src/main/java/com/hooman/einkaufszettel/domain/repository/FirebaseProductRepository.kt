package com.hooman.einkaufszettel.domain.repository

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FirebaseProductRepository {

    suspend fun insertProduct(product: Product):Flow<Resource<Unit>>

    suspend fun getAllProducts():Flow<Resource<List<Product>>>

    suspend fun getProductByName(name:String):Flow<Resource<List<Product>>>

    suspend fun getProductById(id:Long):Flow<Resource<List<Product>>>

    suspend fun deleteProduct(id:Long):Flow<Resource<Unit>>
}