package com.palanivelraghul.doodlebluemart.activity

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.palanivelraghul.doodlebluemart.R
import com.palanivelraghul.doodlebluemart.adapter.MenuItemListAdapter
import com.palanivelraghul.doodlebluemart.baseUtils.BaseActivity
import com.palanivelraghul.doodlebluemart.databinding.ActivityCartBinding
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.room.MartDatabase
import com.palanivelraghul.doodlebluemart.utils.AppUtils
import com.palanivelraghul.doodlebluemart.viewmodel.CartActivityViewModel

class CartActivity : BaseActivity(), MenuItemListAdapter.MenuItemListAdapterCallBack, CartActivityViewModel.CartActivityViewModelCallBack {

    private var adapter: MenuItemListAdapter? = null
    private lateinit var viewModel: CartActivityViewModel
    private lateinit var mBinding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCartBinding.inflate(layoutInflater)
        mBinding.viewmodel = onCreateViewModel()
        mBinding.callBack = this
        setUpToolbar()
        radioButtonSelection()
        setContentView(mBinding.root)
    }

    private fun radioButtonSelection() {
        mBinding.rbDineIn.setOnCheckedChangeListener { compoundButton, isChecked ->
            compoundButton.isChecked = isChecked
            if (isChecked) {
                mBinding.rbTakeAway.isChecked = false
            }
        }
        mBinding.rbTakeAway.setOnCheckedChangeListener { compoundButton, isChecked ->
            compoundButton.isChecked = isChecked
            if (isChecked) {
                mBinding.rbDineIn.isChecked = false
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(mBinding.toolBar)
        supportActionBar!!.title = getString(R.string.text_my_cart)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        mBinding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onCreateViewModel(): CartActivityViewModel? {
        viewModel = ViewModelProvider(this).get(CartActivityViewModel::class.java)
        viewModel.initiate(this, initiateDataBase())
        return viewModel
    }

    private fun initiateDataBase(): MartDatabase {
        return MartDatabase.getMartDatabase(this)
    }

    override fun showProgressBar() {
        showMainProgressBar(this)
    }

    override fun dismissProgressBar() {
        dismissMainProgressBar()
    }

    override fun onClickOfMore() {
        showProgressBar()
        viewModel.removingItemPosition = -1
        viewModel.updatingPosition = -1
        if (getString(R.string.text_show_more) == mBinding.tvShowMore.text) {
            mBinding.tvShowMore.text = getString(R.string.text_show_less)
        } else {
            mBinding.tvShowMore.text = getString(R.string.text_show_more)
        }
        viewModel.getSelectedMenuItemList()
    }

    override fun loadMenuList(menuList: List<ItemEntity>) {
        if (menuList.isEmpty()) {
            onBackPressed()
        } else {
            mBinding.tvShowMore.paintFlags = mBinding.tvShowMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            mBinding.tvShowMore.visibility = if (menuList.size > 2) View.VISIBLE else View.GONE
            if (adapter == null) {
                adapter = MenuItemListAdapter(this, getListBasedOnMoreVisibility(menuList), true)
                mBinding.rvSelectedMenuList.adapter = adapter
                mBinding.rvSelectedMenuList.isNestedScrollingEnabled = false
                mBinding.rvSelectedMenuList.layoutManager = LinearLayoutManager(this)
            } else {
                adapter!!.updateMenuData(getListBasedOnMoreVisibility(menuList), viewModel.updatingPosition, viewModel.removingItemPosition)
            }
        }
    }

    private fun getListBasedOnMoreVisibility(menuList: List<ItemEntity>): MutableList<ItemEntity> {
        return if (getString(R.string.text_show_more) == mBinding.tvShowMore.text) {
            menuList.toMutableList().subList(0, if (menuList.size > 2) 2 else menuList.size)
        } else {
            menuList.toMutableList()
        }
    }

    override fun onPlaceOrderClick() {
        Toast.makeText(this, getString(R.string.text_order_placed_successfully), Toast.LENGTH_SHORT).show()
    }

    override fun loadTotalAmountOfCart(totalAmount: Double) {
        mBinding.tvTotalCost.text = AppUtils.getCurrencyFormatedValue(totalAmount)
    }

    override fun itemQuantityIncrease(itemId: Long, previousSelectedQty: Int, stockQuantity: Int, position: Int) {
        if (previousSelectedQty < stockQuantity) {
            viewModel.updateItemRemovePosition(-1)
            viewModel.updatePosition(position)
            viewModel.updateItemSelectedQuantity(previousSelectedQty + 1, itemId)
        }
    }

    override fun itemQuantityDecrease(itemId: Long, previousSelectedQty: Int, stockQuantity: Int, position: Int) {
        viewModel.updatePosition(position)
        viewModel.updateItemRemovePosition(if (previousSelectedQty - 1 == 0) position else -1)
        viewModel.updateItemSelectedQuantity(previousSelectedQty - 1, itemId)
    }

    override fun onMessageClick(itemId: Long) {
        Toast.makeText(this, getString(R.string.text_add_note), Toast.LENGTH_SHORT).show()
    }


}
