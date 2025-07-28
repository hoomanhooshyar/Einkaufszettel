package com.hooman.einkaufszettel.domain.model

import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity

data class ShoppingItem(
    val id:String,
    val billId: Long,
    val productId:Long,
    val itemCount:Int,
    val productName:String,
    val productPrice:Double,
    val productImage:String?,
    val userId:String

){
    fun toShoppingItemEntity():ShoppingItemEntity{
        return ShoppingItemEntity(
            id = id,
            billId = billId,
            productId = productId,
            itemCount = itemCount
        )
    }
}
