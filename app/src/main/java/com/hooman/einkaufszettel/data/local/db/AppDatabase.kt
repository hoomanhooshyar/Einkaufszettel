package com.hooman.einkaufszettel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hooman.einkaufszettel.data.local.converter.Converters
import com.hooman.einkaufszettel.data.local.dao.AppDao
import com.hooman.einkaufszettel.data.local.entity.BillEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity

@Database(
    entities = [BillEntity::class,ShoppingItemEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract val dao:AppDao
}