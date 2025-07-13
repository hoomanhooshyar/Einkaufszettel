package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.repository.FirebaseProductRepository
import kotlinx.coroutines.flow.Flow

class DeleteProductFromRemoteUseCase(
    private val repository: FirebaseProductRepository
) {
    suspend operator fun invoke(id:Long):Flow<Resource<Unit>>{
        return repository.deleteProduct(id)
    }
}