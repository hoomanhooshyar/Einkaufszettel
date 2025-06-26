package com.hooman.einkaufszettel.domain.usecase

import com.hooman.einkaufszettel.core.util.Resource
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class InsertShoppingItemUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem):Flow<Resource<Unit>>{
        return repository.insertShoppingItem(shoppingItem)
    }
}