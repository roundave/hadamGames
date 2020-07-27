package com.davidmartinez.hadamgames.ui.menu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.Usuario
import com.davidmartinez.hadamgames.showMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_fr_perfil.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class fr_perfil : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1234
    var idFirebase:String? =""
    val mAuth: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val id= mAuth?.uid
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usuarios")


    override fun onResume() {
        super.onResume()
        (requireActivity()  as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity()  as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarPerfil()

        bt_camara.setOnClickListener {
            dispatchTakePictureIntent()
        }

        /*val geocoder=Geocoder(context)// para guardar la possicion de la direccion
        //val direccion=
        var longitud=0.0
        var latitud=0.0

        var list:MutableList<Address> = mutableListOf()

        try{
           // list= geocoder.getFromLocationName(direccion, 1)
        }catch (e: IOException){
            Log.d("error",e.message.toString())
        }
        if(list.size!=0){
            Log.d("direccion",list.toString())
            val address:Address=list[0]
            if(address.hasLatitude())
                latitud=address.latitude
            if(address.hasLongitude())
                longitud=address.longitude
        }else{
            showMessage(requireContext(),"Direccion no encontrada")
        }*/


    }

    private fun cargarPerfil() {

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val usuario = datasnapshot.getValue(Usuario::class.java)
                    if (usuario?.id ==id) {
                        tv_nombre_perfil.setText(usuario?.nombre)
                        tv_genero_perfil.setText(usuario?.genero)

                    }
                }
                Log.d("data", snapshot.toString())

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { TakePictureIntent ->
            TakePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(TakePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_foto_perfil.setImageBitmap(imageBitmap)
        }

    }

    private fun actualizarFirebase(nombre: String, telefono: String, genero: String) {
        //base de datos con firebase


        val mStorage = FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)
        var urlPhoto = ""

        // Get the data from an ImageView as bytes
        iv_foto_perfil.isDrawingCacheEnabled = true
        iv_foto_perfil.buildDrawingCache()
        val bitmap = (iv_foto_perfil.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                urlPhoto = task.result.toString()
                val deudor = Usuario(
                    id,
                    nombre,
                    telefono,
                    genero,
                    urlPhoto
                )
                myRef.child(id).setValue(deudor)
                /////////////
            } else {
                // Handle failures
                // ...
            } } }




}