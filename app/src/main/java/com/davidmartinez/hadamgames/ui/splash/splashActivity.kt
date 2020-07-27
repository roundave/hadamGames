package com.davidmartinez.hadamgames.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.davidmartinez.hadamgames.MainActivity
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.ui.login.loginActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.timerTask

class splashActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        setContentView(R.layout.activity_splash_activity)
        val user = mAuth.currentUser



        val timer = Timer()
        timer.schedule(
            timerTask {
                if (user != null)
                    GotoMainActivity()
                else
                    goTologin()
            }, 2000
        )
    }

    private fun goTologin() {
        startActivity(Intent(this, loginActivity::class.java))
    }

    private fun GotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}