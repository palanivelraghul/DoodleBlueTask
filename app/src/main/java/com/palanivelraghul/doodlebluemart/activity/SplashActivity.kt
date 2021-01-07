package com.palanivelraghul.doodlebluemart.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.palanivelraghul.doodlebluemart.baseUtils.BaseActivity
import com.palanivelraghul.doodlebluemart.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
        moveNextScreen()
    }

    private fun moveNextScreen() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, ShopItemListActivity::class.java))
            finish()
        }, 2000)
    }
}