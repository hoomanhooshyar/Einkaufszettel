package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.source.FirebaseProductDataSource
import com.hooman.einkaufszettel.domain.source.FirebaseService
import javax.inject.Inject

class FirebaseProductDataSourceImpl @Inject constructor(
    private val firebaseService: FirebaseService
):FirebaseProductDataSource {
    override suspend fun getAllProductsByUserId(userId:String):List<Product> = firebaseService.getAllProductsByUserId(userId)

    override suspend fun getProductByName(name:String):List<Product> = firebaseService.getProductByName(name)

    override suspend fun getProductById(id:Long):List<Product> = firebaseService.getProductById(id)

    override suspend fun insertProduct(product: Product) = firebaseService.insertProduct(product)

    override suspend fun deleteProduct(id:Long) = firebaseService.deleteProduct(id)
}