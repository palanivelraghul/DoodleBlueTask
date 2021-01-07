package com.palanivelraghul.doodlebluemart.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.palanivelraghul.doodlebluemart.R
import com.palanivelraghul.doodlebluemart.adapter.MenuItemListAdapter
import com.palanivelraghul.doodlebluemart.baseUtils.BaseActivity
import com.palanivelraghul.doodlebluemart.databinding.ActivityShopProductListBinding
import com.palanivelraghul.doodlebluemart.entity.ItemEntity
import com.palanivelraghul.doodlebluemart.room.MartDatabase
import com.palanivelraghul.doodlebluemart.viewmodel.ShopItemActivityViewModel

class ShopItemListActivity : BaseActivity(), MenuItemListAdapter.MenuItemListAdapterCallBack,
    ShopItemActivityViewModel.ShopItemActivityViewModelCallBack {

    lateinit var viewModel: ShopItemActivityViewModel
    private lateinit var mBinding: ActivityShopProductListBinding
    lateinit var martDB: MartDatabase
    var adapter: MenuItemListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityShopProductListBinding.inflate(layoutInflater)
        setUpToolbar()
        setContentView(mBinding.root)
        initiateDataBase()
        mBinding.viewModel = onCreateViewModel()
    }

    private fun setUpToolbar() {
        setSupportActionBar(mBinding.toolBar)
        supportActionBar!!.title = " "
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        mBinding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMenuList()
    }

    private fun initiateDataBase() {
        martDB = MartDatabase.getMartDatabase(this)
    }

    private fun onCreateViewModel(): ShopItemActivityViewModel {
        viewModel = ViewModelProvider(this).get(ShopItemActivityViewModel::class.java)
        viewModel.initiate(martDB, this)
        return viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_rest_menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onMenuClick()
        return true
    }

    override fun loadMenuList(menuList: List<ItemEntity>) {
        if (adapter == null) {
            adapter = MenuItemListAdapter(this, menuList.toMutableList(), false)
            mBinding.rvItemList.adapter = adapter
            mBinding.rvItemList.isNestedScrollingEnabled = false
            mBinding.rvItemList.layoutManager = LinearLayoutManager(this)
        } else {
            adapter!!.updateMenuData(menuList.toMutableList(), viewModel.updatingPosition, -1)
        }
    }

    override fun navigateToCart() {
        viewModel.updatingPosition = -1
        startActivity(Intent(this, CartActivity::class.java))
    }

    override fun selectedItem(size: Int) {
        mBinding.trViewCart.visibility = if (size > 0) View.VISIBLE else View.GONE
        mBinding.tvViewCartWithCount.text = String.format(getString(R.string.text_view_cart_with_count), size.toString())
    }

    override fun onMenuClick() {
        showToastMessage(getString(R.string.text_menu))
    }

    override fun onBookATableClick() {
        showToastMessage(getString(R.string.text_book_a_table))
    }

    override fun itemQuantityIncrease(itemId: Long, previousSelectedQty: Int, stockQuantity: Int, position: Int) {
        if (previousSelectedQty < stockQuantity) {
            viewModel.updatePosition(position)
            viewModel.updateItemSelectedQuantity(previousSelectedQty + 1, itemId)
        }
    }

    override fun itemQuantityDecrease(itemId: Long, previousSelectedQty: Int, stockQuantity: Int, position: Int) {
        viewModel.updatePosition(position)
        viewModel.updateItemSelectedQuantity(previousSelectedQty - 1, itemId)
    }

    override fun onMessageClick(itemId: Long) {
        showToastMessage(getString(R.string.text_add_note))
    }

    override fun showProgressBar() {
        showMainProgressBar(this)
    }

    override fun dismissProgressBar() {
        dismissMainProgressBar()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}


