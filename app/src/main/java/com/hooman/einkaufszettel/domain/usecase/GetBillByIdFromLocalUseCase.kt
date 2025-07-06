package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetBillByIdFromLocalUseCase(
    private val repository: AppRepository
) {
    operator fun invoke(id:Long):Flow<Resource<Bill>>{
        return repository.getBillById(id)
    }
}