package com.hooman.einkaufszettel.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import java.util.Date

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromShoppingItemList(items:List<ShoppingItemEntity>):String{
        return gson.toJson(items)
    }

    @TypeConverter
    fun toShoppingItemList(json:String):List<ShoppingItemEntity>{
        val type = object : TypeToken<List<ShoppingItemEntity>>() {}.type
        return gson.fromJson(json,type)
    }

    @TypeConverter
    fun fromDate(date: Date):Long = date.time

    @TypeConverter
    fun toDate(millis:Long):Date = Date(millis)
}