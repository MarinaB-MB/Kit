package com.deadely.ege.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.deadely.ege.R
import com.deadely.ege.ui.main.MainActivity

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startMainActivity()
        }, 1500)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
//        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha)
        finish()
    }

}