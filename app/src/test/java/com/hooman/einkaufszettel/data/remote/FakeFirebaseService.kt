package com.hooman.einkaufszettel.data.remote

import com.hooman.einkaufszettel.domain.model.Bill
import com.hooman.einkaufszettel.domain.model.Product
import com.hooman.einkaufszettel.domain.model.ShoppingItem
import com.hooman.einkaufszettel.domain.source.FirebaseService

class FakeFirebaseService(
    val billDataSource: FakeFirebaseBillDataSource = FakeFirebaseBillDataSource(),
    val productDataSource:FakeFirebaseProductDataSource = FakeFirebaseProductDataSource(),
    val shoppingItemDataSource:FakeFirebaseShoppingItemDataSource = FakeFirebaseShoppingItemDataSource()
)