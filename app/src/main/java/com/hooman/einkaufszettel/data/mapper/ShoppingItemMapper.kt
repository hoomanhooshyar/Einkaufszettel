package com.hooman.einkaufszettel.data.mapper

import com.hooman.einkaufszettel.data.local.entity.ProductEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import com.hooman.einkaufszettel.domain.model.ShoppingItem

fun ShoppingItemEntity.toShoppingItem(product:ProductEntity):ShoppingItem{
    return ShoppingItem(
        id = id,
        productId = product.id,
        itemCount = itemCount,
        productName = product.name,
        productPrice = product.price,
        productImage = product.image,
        userId = "",
        billId = billId
    )
}