package com.hooman.einkaufszettel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hooman.einkaufszettel.data.local.entity.BillEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill:BillEntity)

    @Delete
    suspend fun deleteBill(bill: BillEntity)

    @Query("SELECT * FROM billentity ORDER BY billDate DESC")
    fun getAllBills(): Flow<List<BillEntity>>

    @Query("SELECT * FROM billentity WHERE id = :id LIMIT 1")
    fun getBillById(id:Long):Flow<BillEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Delete
    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)
}