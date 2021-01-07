package com.palanivelraghul.doodlebluemart.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.utils.AppConstant

@Database(entities = [ItemEntity::class], version = 1)
abstract class MartDatabase : RoomDatabase() {

    abstract fun martDAO(): MartDAO

    companion object {
        var instance: MartDatabase? = null

        fun getMartDatabase(context: Context): MartDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MartDatabase::class.java,
                    AppConstant.mDATABASE_NAME
                ).build()
            }
            return instance as MartDatabase
        }
    }
}