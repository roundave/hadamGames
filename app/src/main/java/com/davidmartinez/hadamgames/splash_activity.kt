package com.davidmartinez.hadamgames

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.concurrent.timerTask

class splash_activity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        setContentView(R.layout.activity_splash_activity)


        val timer = Timer()
        timer.schedule(
            timerTask {
                goTologin()
            }, 2000
        )
    }

    private fun goTologin() {
        val intent = Intent(this, login2_activity::class.java)
        startActivity(intent)
        finish()
    }
}