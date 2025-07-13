package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.repository.FirebaseProductRepository
import kotlinx.coroutines.flow.Flow

class GetAllProductsFromRemoteUseCase(
    private val repository: FirebaseProductRepository
) {
    suspend operator fun invoke():Flow<Resource<List<Product>>>{
        return repository.getAllProducts()
    }
}