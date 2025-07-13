package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class GetProductByIdFromLocalUseCase(
    private val repository: LocalRepository
) {
    operator fun invoke(id:Long):Flow<Resource<Product>>{
        return repository.getProductById(id)
    }
}