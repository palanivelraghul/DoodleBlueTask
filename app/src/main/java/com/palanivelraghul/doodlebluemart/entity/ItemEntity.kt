package com.palanivelraghul.doodlebluemart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ItemList")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long,
    @ColumnInfo(name = "ItemName")
    var itemName: String,
    @ColumnInfo(name = "ItemImage")
    var itemImage: Int,
    @ColumnInfo(name = "isDayItem")
    var isDayItem: Boolean,
    @ColumnInfo(name = "isNightItems")
    var isNightItems: Boolean,
    @ColumnInfo(name = "ItemStockQuantity")
    var itemStockQuantity: Int,
    @ColumnInfo(name = "ItemSelectedQuantity")
    var itemSelectedQuantity: Int,
    @ColumnInfo(name = "ItemDescription")
    var itemDescription: String,
    @ColumnInfo(name = "ItemOffer")
    var itemOffer: String,
    @ColumnInfo(name = "ItemPrice")
    var itemPrice: Double
)