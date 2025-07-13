package com.hooman.einkaufszettel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val name:String,
    val image:String?,
    val price:Double
)