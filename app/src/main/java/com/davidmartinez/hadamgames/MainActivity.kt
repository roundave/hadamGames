package com.davidmartinez.hadamgames

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.davidmartinez.hadamgames.model.remote.Usuario
import com.davidmartinez.hadamgames.ui.login.loginActivity
import com.davidmartinez.hadamgames.ui.menu.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var novedadesload = true
    val manager = supportFragmentManager
    var password = String()
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance() // firebase
    val User: FirebaseUser? = mAuth.currentUser
    val id=mAuth.uid
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usuarios").child(id!!)
    var urlFoto:String=""

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        var menu=navView.menu.findItem(R.id.navegation_perfil)
        //StatusBarUtil.setTransparent(this)// cambia el color de la barra de status
       // StatusBarUtil.setColor(this,1)
        obtenerfotoPerfil(menu)
        mostrarMensageBienvenida()
        navView.itemIconTintList=null
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navegation_perfil,
                R.id.navegation_novedades,
                R.id.navegation_tienda,
                R.id.navegation_evento,
                R.id.navigation_biblioteca
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navView.setupWithNavController(navController)




        /*bt_novedades.setOnClickListener {

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
        bt_perfil.setOnClickListener{
            showfragmentperfil()

        }*/
    }

    private fun obtenerfotoPerfil(menu: MenuItem) {
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Usuario::class.java)
                if (usuario != null) {
                    urlFoto = usuario.fotoPerfil
                    cargarFotoPerfilBar(menu, urlFoto)
                }
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun cargarFotoPerfilBar(menu: MenuItem, urlFoto: String) {
        Glide.with(this)
            .asBitmap()
            .load(urlFoto)
            .apply(
                RequestOptions
                    .circleCropTransform()
                    .placeholder(R.drawable.ic_player_placeholder)
            )
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?

                ) {
                    val foto: Drawable = BitmapDrawable(resources, resource)
                    menu.setIcon(foto)


                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
   /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> {
                goTologin()
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    private fun mostrarMensageBienvenida() {

        val correo = User?.email
        Toast.makeText(this, "Bienvenido $correo", Toast.LENGTH_SHORT).show()
    }


    fun goTologin() {
        val intent = Intent(this, loginActivity::class.java)
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
        val fragment = fr_novedades()
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
        val fragment= fr_evento()
        transaction.setCustomAnimations(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        }

    fun showfragmenttienda() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment= fr_tienda()
        transaction.setCustomAnimations(R.anim.slide_left_right_enter, R.anim.slide_left_right_exit)
        transaction.replace(R.id.frameLayout2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}