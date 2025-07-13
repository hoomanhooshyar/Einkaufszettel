package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Product
import javax.inject.Inject

class FirebaseProductDataSource @Inject constructor(
    private val firebaseService: FirebaseService
) {
    suspend fun getAllProducts():List<Product> = firebaseService.getAllProducts()

    suspend fun getProductByName(name:String):List<Product> = firebaseService.getProductByName(name)

    suspend fun getProductById(id:Long):List<Product> = firebaseService.getProductById(id)

    suspend fun insertProduct(product: Product) = firebaseService.insertProduct(product)

    suspend fun deleteProduct(id:Long) = firebaseService.deleteProduct(id)
}