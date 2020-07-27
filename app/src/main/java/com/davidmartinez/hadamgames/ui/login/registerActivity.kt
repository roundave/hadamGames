package com.davidmartinez.hadamgames.ui.login

import android.app.DatePickerDialog
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
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.Usuario
import com.davidmartinez.hadamgames.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class registerActivity : AppCompatActivity() {

    lateinit var option: Spinner
    lateinit var result: TextView
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance() // firebase

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_register)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        Log.d("Oncreate", "ok")

        var mAuth: FirebaseAuth = FirebaseAuth.getInstance() // firebase


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val timer = Timer()
        /////////firebase/////////

        bt_fecha.setOnClickListener {
            mostrar_fecha(year, month, day)
        }

        bt_guardar.setOnClickListener {

            val nombre = et_nombre.text.toString()
            val telefono = et_telefono.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val repcontrasena = et_confirmar_password.text.toString()
            val genero = if (rb_masculino.isChecked) "Masculino" else "Femenino"
            val fecha = tv_fecha.text.toString()
            if (password.length < 6)
                et_password.error = "contraseña muy corto"
            if (nombre.trim().isEmpty()) {
                Toast.makeText(this, "ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
            if (telefono.trim().isEmpty()) {
                Toast.makeText(this, "Ingrese un numero de telefono", Toast.LENGTH_SHORT).show()
            }
            if (email.trim().isEmpty()) {
                Toast.makeText(this, "ingrese un correo", Toast.LENGTH_SHORT).show()
            }
            if (password.trim().isEmpty()) {
                Toast.makeText(this, "ingrese una contraseña", Toast.LENGTH_SHORT).show()
            }

            /* if (ciudadnacimiento.trim().isEmpty()) {
                 Toast.makeText(this, "Ingrese una ciudad ", Toast.LENGTH_SHORT).show()
             }*/
            if (genero.trim().isEmpty()) {
                Toast.makeText(this, "Seleccione el genero", Toast.LENGTH_SHORT).show()
            }
            if (fecha.trim().isEmpty()) {
                Toast.makeText(this, "ingrese una fecha ", Toast.LENGTH_SHORT).show()
            }
            /*if (pasatiempos.trim().isEmpty()) {
                Toast.makeText(this, "Seleccione al menos un pasatiempo", Toast.LENGTH_SHORT)
                    .show()
            }*/
            if (password != repcontrasena && password.trim().isNotEmpty()) {
                Toast.makeText(this, "contraseñas no conciden ", Toast.LENGTH_SHORT).show()
            }

            if (password == repcontrasena && password.trim().isNotEmpty() && nombre.trim()
                    .isNotEmpty() && email.trim().isNotEmpty()
                && genero.trim().isNotEmpty() && telefono.trim()
                    .isNotEmpty() && password.length >= 6)
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {

                            //crearUsuarioEnBaseDeDatos()
                            guardarEnFirebase(nombre, telefono, genero, fecha)
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            onBackPressed()

                        } else {

                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            val mensaje= task.exception!!.message.toString()
                            showMessage(this,mensaje)


                        }

                        // ...
                    }
            }
        }}

        private fun guardarEnFirebase(nombre: String, telefono: String, genero: String, fecha: String) {
            //base de datos con firebase
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("usuarios")
            val id =mAuth?.uid
            val usuario = Usuario(
                id, nombre, telefono, genero, fecha
            )
            myRef.child(id!!).setValue(usuario)
            /////////////

        }


    private fun mostrar_fecha(year: Int, month: Int, day: Int) {
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
        //////////////////////////////////////////////////


        ///// creamos las variables para guardar
        //bt_guardar.setOnClickListener {


        // val ciudadnacimiento = sp_ciudad_nacimiento.selectedItem.toString()
        /*var pasatiempos = ""
        if (ch_cine.isChecked) pasatiempos = "$pasatiempos cine" // sirve para concatenar
        if (ch_videojuegos.isChecked) pasatiempos = "$pasatiempos videojuegos"
        if (ch_cocinar.isChecked) pasatiempos = "$pasatiempos cocinar"*/


/////////////////////////////////////////////////////////////////////////////////

        /////cargar la app de la fecha


        ///////// //validar campos vacios

        /*tv_resultado.text =
            "Nombre: $nombre \nTelefono: $telefono \nEmail: $correo \npasatiempo: $pasatiempos \nCiudad de nacimiento: $ciudadnacimiento \n" +
                    "fecha de nacimiento: $fecha "
        val usuario = Usuario(NULL, nombre, correo, contrasena)
        val UsuarioDAO: UsuarioDAO = sesionROOM.database2.UsuarioDAO()
        UsuarioDAO.crearUsuario(usuario)

        //Toast.makeText(this, "Registro exitoso ", Toast.LENGTH_LONG).show()
        timer.schedule(
            timerTask {
                goTologin()
            }, 3000
        )


    } else if (contrasena != repcontrasena && contrasena.trim().isNotEmpty()) {
    Toast.makeText(this, "contraseñas no conciden ", Toast.LENGTH_SHORT).show()
}
} */









    /*override fun onBackPressed() {
        super.onBackPressed()
        finish()
        // overridePendingTransition(R.anim.slide_bottom_top_enter_formulario,R.anim.slide_bottom_top_exit_formulario)

    }*/


    /*override fun onStart() {
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
    }*/


}






