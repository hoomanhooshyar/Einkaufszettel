package com.hooman.einkaufszettel.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hooman.einkaufszettel.domain.model.ShoppingItem

@Entity(
    tableName = "shopping_items",
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["billId"])]
    )
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val billId:Long,
    val itemName:String,
    val itemCount:Int,
    val price:Double,
    val image:String
){
    fun toShoppingItem():ShoppingItem {
        return ShoppingItem(
            id = id,
            billId = billId,
            itemName = itemName,
            itemCount = itemCount,
            price = price,
            image = image
        )
    }
}
