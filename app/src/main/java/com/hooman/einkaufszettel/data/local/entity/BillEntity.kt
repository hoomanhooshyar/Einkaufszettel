package com.hooman.einkaufszettel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hooman.einkaufszettel.domain.model.Bill
import java.util.Date

@Entity(tableName = "bill")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val billDate:Date,

)