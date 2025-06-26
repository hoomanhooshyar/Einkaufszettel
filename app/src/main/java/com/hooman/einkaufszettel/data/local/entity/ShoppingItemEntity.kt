package com.hooman.einkaufszettel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hooman.einkaufszettel.domain.model.ShoppingItem

@Entity
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val itemName:String,
    val itemCount:Int,
    val price:Double,
    val image:String
){
    fun toShoppingItem():ShoppingItem {
        return ShoppingItem(
            id = id,
            itemName = itemName,
            itemCount = itemCount,
            price = price,
            image = image
        )
    }
}
