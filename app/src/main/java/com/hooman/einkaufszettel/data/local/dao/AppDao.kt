package com.hooman.einkaufszettel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hooman.einkaufszettel.data.local.entity.BillEntity
import com.hooman.einkaufszettel.data.local.entity.ShoppingItemEntity
import com.hooman.einkaufszettel.data.local.relation.BillWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill:BillEntity)

    @Delete
    suspend fun deleteBill(bill: BillEntity)

    @Transaction
    @Query("SELECT * FROM bill ORDER BY billDate DESC")
    fun getAllBills(): Flow<List<BillWithItems>>

    @Transaction
    @Query("SELECT * FROM bill WHERE id = :id LIMIT 1")
    fun getBillById(id:Long):Flow<BillWithItems?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItemEntity: ShoppingItemEntity)

    @Delete
    suspend fun deleteShoppingItem(shoppingItemEntity: ShoppingItemEntity)
}