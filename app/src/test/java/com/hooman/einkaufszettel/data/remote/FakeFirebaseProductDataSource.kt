package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.source.FirebaseProductDataSource

class FakeFirebaseProductDataSource:FirebaseProductDataSource {

    private val productList = mutableListOf<Product>()

    override suspend fun getAllProductsByUserId(userId: String): List<Product> {
        return productList.filter { it.userId == userId }
    }

    override suspend fun getProductByName(name: String): List<Product> {
        return productList.filter { it.name == name }
    }

    override suspend fun getProductById(id: Long): List<Product> {
        return productList.filter { it.id == id }
    }

    override suspend fun insertProduct(product: Product) {
        productList.removeIf { it.id == product.id }
        productList.add(product)
    }

    override suspend fun deleteProduct(id: Long) {
        productList.removeIf{it.id == id}
    }
}