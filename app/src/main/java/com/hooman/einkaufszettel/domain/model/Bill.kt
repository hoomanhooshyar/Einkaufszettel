package com.hooman.einkaufszettel.domain.model

import com.google.gson.Gson
import com.hooman.einkaufszettel.data.local.entity.BillEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import java.util.Date

data class Bill(
    val id:Long,
    val billDate: Date,
    val items: List<ShoppingItem>
){
    fun toBillEntity():BillEntity {

        return BillEntity(
            id = id,
            billDate = billDate,

        )
    }
}
