package com.davidmartinez.hadamgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var password= String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val correo=intent . getStringExtra ("correo")
         password=intent . getStringExtra ("password")
        tv_correo_main_activity.setText(correo)
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.close->{ goTologin()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun goTologin() {
        val intent = Intent(this, login2_activity::class.java)
        intent.putExtra("correo", tv_correo_main_activity.text.toString())
        intent.putExtra("password", password)
        startActivity(intent)
        finish()
    }
}