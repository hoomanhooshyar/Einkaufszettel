package com.hooman.einkaufszettel.domain.source

import com.hooman.einkaufszettel.domain.model.Product

interface FirebaseProductDataSource {
    suspend fun getAllProductsByUserId(userId:String):List<Product>
    suspend fun getProductByName(name:String):List<Product>
    suspend fun getProductById(id:Long):List<Product>
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(id:Long)
}