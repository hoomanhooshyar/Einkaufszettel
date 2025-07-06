package com.hooman.einkaufszettel.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import java.util.Date

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDate(date: Date):Long = date.time

    @TypeConverter
    fun toDate(millis:Long):Date = Date(millis)
}