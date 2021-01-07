package com.palanivelraghul.doodlebluemart.viewmodel

import androidx.lifecycle.ViewModel
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.room.MartDatabase
import com.palanivelraghul.doodlebluemart.utils.AppUtils
import kotlinx.coroutines.*

class ShopItemActivityViewModel() : ViewModel() {

    var updatingPosition: Int = -1
    private lateinit var martDB: MartDatabase
    lateinit var callBack: ShopItemActivityViewModelCallBack

    fun initiate(martDB: MartDatabase, callBack: ShopItemActivityViewModelCallBack) {
        this.martDB = martDB
        this.callBack = callBack
        callBack.showProgressBar()
        getMenuList()
    }

    fun getMenuList() {
        var menuList = listOf<ItemEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                menuList = martDB.martDAO().getAllItems()
            }
            withContext(Dispatchers.Main) {
                job.await()
                if (menuList.isEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val job = async {
                            AppUtils.insertData(martDB)
                        }
                        withContext(Dispatchers.Main) {
                            job.await()
                            getMenuList()
                        }
                    }
                } else {
                    callBack.loadMenuList(menuList)
                    getSelectedMenuList()
                }

            }
        }
    }

    private fun getSelectedMenuList() {
        var menuList = listOf<ItemEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                menuList = martDB.martDAO().getSelectedItemsList()
            }
            withContext(Dispatchers.Main) {
                job.await()
                callBack.selectedItem(menuList.size)
                callBack.dismissProgressBar()
            }
        }
    }

    fun updateItemSelectedQuantity(selectedValue: Int, itemId: Long) {
        callBack.showProgressBar()
        CoroutineScope(Dispatchers.IO).launch {
            val job = async {
                martDB.martDAO().updateItemSelectedQuantity(selectedValue, itemId)
            }
            withContext(Dispatchers.Main) {
                job.await()
                getMenuList()
            }
        }
    }

    fun navigateToCart() {
        callBack.navigateToCart()
    }

    fun onMenuClick() {
        callBack.onMenuClick()
    }

    fun onBookATableClick() {
        callBack.onBookATableClick()
    }

    fun updatePosition(position: Int) {
        updatingPosition = position
    }

    interface ShopItemActivityViewModelCallBack {
        fun showProgressBar()
        fun dismissProgressBar()
        fun loadMenuList(menuList: List<ItemEntity>)
        fun navigateToCart()
        fun selectedItem(size: Int)
        fun onMenuClick()
        fun onBookATableClick()
    }

}