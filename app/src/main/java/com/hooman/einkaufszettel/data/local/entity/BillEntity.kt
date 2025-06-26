package com.hooman.einkaufszettel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hooman.einkaufszettel.domain.model.Bill
import java.util.Date

@Entity
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val billDate:Date,
    val itemsJson:String
){
    fun toBill():Bill {
        val type = object : TypeToken<List<ShoppingItemEntity>>() {}.type
        val entityList: List<ShoppingItemEntity> = Gson().fromJson(itemsJson, type)
        val items = entityList.map { it.toShoppingItem() }
        return Bill(
            id = id,
            billDate = billDate,
            items = items
        )
    }
}