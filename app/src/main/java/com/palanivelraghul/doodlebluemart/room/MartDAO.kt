package com.palanivelraghul.doodlebluemart.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palanivelraghul.doodlebluemart.entity.ItemEntity

@Dao
interface MartDAO {

    @Query("Select * from ItemList")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("Select * from ItemList where ItemSelectedQuantity != 0 ")
    suspend fun getSelectedItemsList(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemDetails(itemDetails: ItemEntity)

    @Query("Update ItemList set ItemSelectedQuantity = :selectedQty where itemId=:itemId")
    suspend fun updateItemSelectedQuantity(selectedQty: Int, itemId: Long)

    @Query(" Select sum(ItemSelectedQuantity * ItemPrice) from ItemList")
    suspend fun getTotalCostOfCart(): Double

}