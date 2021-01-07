package com.palanivelraghul.doodlebluemart.baseUtils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.palanivelraghul.doodlebluemart.R


open class BaseActivity : AppCompatActivity() {

    private var mDialog: Dialog? = null

    fun showMainProgressBar(context: Context) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(context, R.style.CustomProgressTheme)
                mDialog?.setContentView(R.layout.custom_progress)
                mDialog?.setCancelable(false)
                mDialog?.setCanceledOnTouchOutside(false)
                mDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissMainProgressBar() {
        try {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog?.dismiss()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        mDialog = null
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected open fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    open fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit();
    }
}