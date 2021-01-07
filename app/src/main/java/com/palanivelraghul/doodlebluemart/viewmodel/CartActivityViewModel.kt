package com.palanivelraghul.doodlebluemart.viewmodel

import androidx.lifecycle.ViewModel
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.room.MartDatabase
import kotlinx.coroutines.*

class CartActivityViewModel : ViewModel() {

    var removingItemPosition: Int = -1
    var updatingPosition: Int = 0
    private lateinit var callBack: CartActivityViewModelCallBack
    private lateinit var martDB: MartDatabase

    fun initiate(callBack: CartActivityViewModelCallBack, martDB: MartDatabase) {
        this.callBack = callBack
        this.martDB = martDB
        callBack.showProgressBar()
        getSelectedMenuItemList()
    }

    private fun calculateTotalAmount() {
        var totalAmount: Double = 0.0
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                totalAmount = martDB.martDAO().getTotalCostOfCart()
            }
            withContext(Dispatchers.Main) {
                job.await()
                callBack.loadTotalAmountOfCart(totalAmount)
                callBack.dismissProgressBar()
            }
        }
    }

    fun getSelectedMenuItemList() {
        var menuList = listOf<ItemEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                menuList = martDB.martDAO().getSelectedItemsList()
            }
            withContext(Dispatchers.Main) {
                job.await()
                calculateTotalAmount()
                callBack.loadMenuList(menuList)
            }
        }
    }

    fun updatePosition(position: Int) {
        updatingPosition = position
    }

    fun updateItemSelectedQuantity(selectedValue: Int, itemId: Long) {
        callBack.showProgressBar()
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                martDB.martDAO().updateItemSelectedQuantity(selectedValue, itemId)
            }
            withContext(Dispatchers.Main) {
                job.await()
                getSelectedMenuItemList()
            }
        }
    }

    fun updateItemRemovePosition(removeItemPosition: Int) {
        removingItemPosition = removeItemPosition
    }


    interface CartActivityViewModelCallBack {
        fun showProgressBar()
        fun dismissProgressBar()
        fun onClickOfMore()
        fun onPlaceOrderClick()
        fun loadMenuList(menuList: List<ItemEntity>)
        fun loadTotalAmountOfCart(totalAmount: Double)
    }


}


