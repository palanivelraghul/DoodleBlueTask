package com.palanivelraghul.doodlebluemart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.palanivelraghul.doodlebluemart.databinding.ItemMenuListBinding
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.utils.AppUtils

class MenuItemListAdapter(var callBack: MenuItemListAdapterCallBack, var itemList: MutableList<ItemEntity>, var isFromCart: Boolean) :
    RecyclerView.Adapter<MenuItemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindData(callBack, itemList[position], isFromCart)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateMenuData(updatedMenuList: MutableList<ItemEntity>, updatingPosition: Int, itemRemovePosition: Int) {
        itemList.clear();
        itemList = updatedMenuList
        if (itemRemovePosition < 0) {
            if (updatingPosition < 0) {
                notifyDataSetChanged()
            } else {
                notifyItemChanged(updatingPosition)
            }
        } else {
            notifyItemRemoved(itemRemovePosition)
        }
    }

    class ViewHolder(var binding: ItemMenuListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindData(callBack: MenuItemListAdapterCallBack, menuItem: ItemEntity, isFromCart: Boolean) {
            binding.callBack = callBack
            binding.item = menuItem
            binding.position = adapterPosition
            binding.isFromCart = isFromCart
            binding.tvItemPrice.text = AppUtils.getCurrencyFormatedValue(menuItem.itemPrice)
        }

    }

    interface MenuItemListAdapterCallBack {
        fun itemQuantityIncrease(itemId: Long, previousSelectedQty: Int, position: Int)
        fun itemQuantityDecrease(itemId: Long, previousSelectedQty: Int, position: Int)
    }
}
