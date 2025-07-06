package com.hooman.einkaufszettel.domain.model

import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity

data class ShoppingItem(
    val id:String,
    val billId:Long,
    val itemName:String,
    val itemCount:Int,
    val price:Double,
    val image:String
){
    fun toShoppingItemEntity():ShoppingItemEntity{
        return ShoppingItemEntity(
            id = id,
            billId = billId,
            itemName = itemName,
            itemCount = itemCount,
            price = price,
            image = image
        )
    }
}
