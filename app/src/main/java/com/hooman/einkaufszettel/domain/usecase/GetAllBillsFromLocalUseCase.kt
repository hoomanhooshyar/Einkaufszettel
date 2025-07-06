package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetAllBillsFromLocalUseCase(
    private val repository: AppRepository
) {
    operator fun invoke():Flow<Resource<List<Bill>>>{
        return repository.getAllBills()
    }
}