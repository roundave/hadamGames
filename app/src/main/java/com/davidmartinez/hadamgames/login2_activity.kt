package com.davidmartinez.hadamgames

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login2_activity.*
import org.w3c.dom.Text

class login2_activity : AppCompatActivity() {

    // var password = " "
    //var correo = " "

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val window: Window = this@login2_activity.getWindow()
        window.statusBarColor = ContextCompat.getColor(this@login2_activity, android.R.color.transparent)
        setContentView(R.layout.activity_login2_activity)


        val password = intent.getStringExtra("password")
        val correo = intent.getStringExtra("correo")
        val hola= et_email_login

        et_password_login.setText(password)
        et_email_login.setText(correo)
        et_password_login.addTextChangedListener(textWatcher)
        et_email_login.addTextChangedListener(textWatcher)
        if(et_email_login.text.isNotEmpty() && et_password_login.text.length>=6)
            bt_login.isEnabled=true



        bt_login.setOnClickListener {
            if (et_email_login.text.toString() == correo && et_password_login.text.toString() == password)
                goToMainActivity()
            else
                Toast.makeText(this, "Correo o Password incorrecto", Toast.LENGTH_SHORT).show()
        }

        bt_registro.setOnClickListener {
            goToformulario()
        }


    }
    //////se usa para algo que contenta la variavle textWatcher cambie realice una acción
    private val textWatcher=object :TextWatcher{
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            bt_login.isEnabled = (et_email_login.text.isNotEmpty() && et_password_login.text.length>=6)
            //if(et_password_login.text.length<6 && et_password_login.)
              //  et_password_login.error="contraseña debe ser mayor de 6 digitos"
        }}


    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("correo", et_email_login.text.toString())
        intent.putExtra("password", et_password_login.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left_right_enter,R.anim.slide_left_right_exit)


    }

    private fun goToformulario() {
        val intent = Intent(this, formulario_activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_bottom_top_enter_formulario,R.anim.slide_bottom_top_exit_formulario)
    }
}