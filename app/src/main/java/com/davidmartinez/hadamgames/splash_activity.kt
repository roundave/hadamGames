package com.davidmartinez.hadamgames

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask

class splash_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
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