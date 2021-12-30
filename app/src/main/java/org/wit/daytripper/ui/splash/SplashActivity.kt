package org.wit.daytripper.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import org.wit.daytripper.R
import org.wit.daytripper.ui.home.Home

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splashscr)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, Home::class.java))
            finish()
        }
    }
}