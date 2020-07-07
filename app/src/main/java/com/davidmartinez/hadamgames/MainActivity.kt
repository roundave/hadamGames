package com.davidmartinez.hadamgames

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bt_eventos
import kotlinx.android.synthetic.main.activity_main.bt_novedades
import kotlinx.android.synthetic.main.activity_main.bt_tienda
import kotlinx.android.synthetic.main.fragment_fr_menu.*
import kotlinx.android.synthetic.main.activity_main.bt_perfill as bt_perfill1


class MainActivity : AppCompatActivity() {
    var novedadesload = true
    val manager = supportFragmentManager
    var password = String()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        setContentView(R.layout.activity_main)
        showfragmentnovedades()

        val correo = intent.getStringExtra("correo")
        password = intent.getStringExtra("password")
        // tv_correo_main_activity.setText(correo)

        bt_novedades.setOnClickListener {

            showfragmentnovedades()
        }
        bt_eventos.setOnClickListener{
            showfragmenteventos()

        }

        bt_tienda.setOnClickListener{
            showfragmenttienda()
        }

        framelayout_menu.setOnClickListener {
            showfragmentmenu()
        }
        bt_perfill.setOnClickListener{
            showfragmentperfil()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> {
                goTologin()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun goTologin() {
        val intent = Intent(this, login2_activity::class.java)
        //intent.putExtra("correo", tv_correo_main_activity.text.toString())
        intent.putExtra("password", password)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
    }

    fun showfragmentnovedades() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = novedades_fragment()
        transaction.setCustomAnimations(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        novedadesload = true}

    fun showfragmentmenu(){
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = fr_menu()
        transaction.setCustomAnimations(R.anim.slide_bottom_top_enter, R.anim.slide_left_right_enter)
        transaction.replace(R.id.layout_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    fun showfragmentperfil(){
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = fr_perfil()
        transaction.setCustomAnimations(R.anim.slide_left_right_exit, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    fun showfragmenteventos() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment=fr_evento()
        transaction.setCustomAnimations(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        }

    fun showfragmenttienda() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment=fr_tienda()
        transaction.setCustomAnimations(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}