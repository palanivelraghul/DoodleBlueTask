package com.palanivelraghul.doodlebluemart.utils

import android.content.Context
import com.palanivelraghul.doodlebluemart.R
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.room.MartDatabase
import java.util.*

object AppUtils {
    suspend fun insertData(martDB: MartDatabase) {
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Rice Idli",
                R.drawable.ic_round_message,
                true,
                false,
                20,
                0,
                "Spicy Rice Idli with other items",
                "",
                60.9
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Sambhar Vada",
                R.drawable.ic_round_message,
                true,
                true,
                20,
                0,
                "Spicy Sambhar Vada with other items",
                "",
                70.9
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Dahi Vada",
                R.drawable.ic_round_message,
                true,
                true,
                20,
                0,
                "Spicy Dahi Vada with other items",
                "",
                156.00
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Tandoori Paneer Tikka",
                R.drawable.ic_round_message,
                false,
                true,
                20,
                0,
                "Spicy Tandoori Paneer Tikka with other items",
                "",
                450.9
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Malai Paneer Tikka",
                R.drawable.ic_round_message,
                true,
                false,
                20,
                0,
                "Spicy Malai Paneer Tikka with other items",
                "",
                97.4
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Tandoori Aloo",
                R.drawable.ic_round_message,
                true,
                true,
                20,
                0,
                "Spicy Tandoori Aloo with other items",
                "",
                350.5
            )
        )
        martDB.martDAO()
            .insertItemDetails(ItemEntity(0, "Platter", R.drawable.ic_round_message, true, true, 20, 0, "Spicy Platter with other items", "", 340.2))
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Dahi ke Kabab",
                R.drawable.ic_round_message,
                true,
                false,
                20,
                0,
                "Spicy Dahi ke Kabab with other items",
                "",
                150.80
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Hare-Bhare Kabab",
                R.drawable.ic_round_message,
                true,
                true,
                20,
                0,
                "Spicy Hare-Bhare Kabab with other items",
                "",
                178.00
            )
        )
        martDB.martDAO().insertItemDetails(
            ItemEntity(
                0,
                "Punjabi Soya Chap",
                R.drawable.ic_round_message,
                false,
                true,
                20,
                0,
                "Spicy Punjabi Soya Chap with other items",
                "",
                140.00
            )
        )
    }

    fun getCurrencyFormatedValue(amount: Double): String {
        return "\u20B9" + String.format(Locale.getDefault(), "%.2f", amount)
    }

}
