package com.davidmartinez.hadamgames

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_formulario_activity.*
import java.util.*
import kotlin.concurrent.timerTask

class formulario_activity : AppCompatActivity() {

    lateinit var option: Spinner
    lateinit var result: TextView
    var contrasena: String = ""
    var correo: String = ""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_formulario_activity)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        Log.d("Oncreate", "ok")


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val timer = Timer()

        bt_fecha.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear: Int, mMonth: Int, mDay: Int ->
                    tv_fecha.text = "" + mDay + "/" + mMonth + "/" + mYear
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        ///// creamos las variables para guardar
        bt_guardar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val telefono = et_telefono.text.toString()
            correo = et_email.text.toString()
            contrasena = et_password.text.toString()
            val repcontrasena = et_confirmar_password.text.toString()
            val genero = if (rb_masculino.isChecked) "Masculino" else "Femenino"
            val ciudadnacimiento = sp_ciudad_nacimiento.selectedItem.toString()
            var pasatiempos = ""
            if (ch_cine.isChecked) pasatiempos = "$pasatiempos cine" // sirve para concatenar
            if (ch_videojuegos.isChecked) pasatiempos = "$pasatiempos videojuegos"
            if (ch_cocinar.isChecked) pasatiempos = "$pasatiempos cocinar"
            if(contrasena.length<6)
                et_password.error="contraseña muy corto"

/////////////////////////////////////////////////////////////////////////////////

            /////cargar la app de la fecha

            val fecha = tv_fecha.text.toString()

            ///////// //validar campos vacios
            if (nombre.trim().isEmpty()) {
                Toast.makeText(this, "ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
            if (telefono.trim().isEmpty()) {
                Toast.makeText(this, "Ingrese un numero de telefono", Toast.LENGTH_SHORT).show()
            }
            if (correo.trim().isEmpty()) {
                Toast.makeText(this, "ingrese un correo", Toast.LENGTH_SHORT).show()
            }
            if (contrasena.trim().isEmpty()) {
                Toast.makeText(this, "ingrese una contraseña", Toast.LENGTH_SHORT).show()
            }

            if (ciudadnacimiento.trim().isEmpty()) {
                Toast.makeText(this, "Ingrese una ciudad ", Toast.LENGTH_SHORT).show()
            }
            if (genero.trim().isEmpty()) {
                Toast.makeText(this, "Seleccione el genero", Toast.LENGTH_SHORT).show()
            }
            if (fecha.trim().isEmpty()) {
                Toast.makeText(this, "ingrese una fecha ", Toast.LENGTH_SHORT).show()
            }
            if (pasatiempos.trim().isEmpty()) {
                Toast.makeText(this, "Seleccione al menos un pasatiempo", Toast.LENGTH_SHORT).show()
            }

            if (contrasena == repcontrasena && contrasena.trim().isNotEmpty() && nombre.trim()
                    .isNotEmpty() && correo.trim().isNotEmpty()
                && fecha.trim().isNotEmpty() && pasatiempos.trim()
                    .isNotEmpty() && ciudadnacimiento.trim().isNotEmpty()
                && genero.trim().isNotEmpty() && telefono.trim().isNotEmpty() &&contrasena.length>=6
            ) {
                tv_resultado.text =
                    "Nombre: $nombre \nTelefono: $telefono \nEmail: $correo \npasatiempo: $pasatiempos \nCiudad de nacimiento: $ciudadnacimiento \n" +
                            "fecha de nacimiento: $fecha "

                Toast.makeText(this, "Registro exitoso ", Toast.LENGTH_LONG).show()
                timer.schedule(
                    timerTask {
                        goTologin()
                    }, 3000
                )


            } else if (contrasena != repcontrasena && contrasena.trim().isNotEmpty()) {
                Toast.makeText(this, "contraseñas no conciden ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun goTologin() {
        val intent = Intent(this, login2_activity::class.java)
        intent.putExtra("password", contrasena)
        intent.putExtra("correo", correo)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_bottom_top_enter_formulario,R.anim.slide_bottom_top_exit_formulario)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_bottom_top_enter_formulario,R.anim.slide_bottom_top_exit_formulario)

    }


    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "ok")
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "ok")
    }

    override fun onPause() {
        super.onPause()
        Log.d("OnPause", "ok")
    }

    override fun onStop() {
        super.onStop()
        Log.d("OnStop", "ok")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("OnRestart", "ok")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("OnDestroy", "ok")
    }


}


