package com.davidmartinez.hadamgames.ui.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fr_perfil.*
import java.io.ByteArrayOutputStream


class fr_perfil : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1234
    var idFirebase: String? = ""
    val mAuth: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val id = mAuth?.uid
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usuarios").child(id!!)


    /*override fun onResume() {
        super.onResume()
        (requireActivity()  as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity()  as AppCompatActivity?)!!.supportActionBar!!.show()
    }*/

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

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                val usuario = snapshot.getValue(Usuario::class.java)
                tv_nombre_perfil.text = "Nombre: ${usuario?.nombre}"
                tv_genero_perfil.text = "Genero: " + usuario?.genero
                tv_fechaNacimientoPerfil.text = "Fecha de nacimiento: ${usuario?.fecha}"
                if (usuario?.fotoPerfil!!.isNotEmpty()) {
                    val url = usuario.fotoPerfil
                    Picasso.get().load(url).placeholder(R.drawable.ic_player_placeholder)
                        .error(R.drawable.ic_error).into(iv_foto_perfil)
                }


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
            actualizarFirebase2(myRef)
        }

    }

    private fun actualizarFirebase2(myRef: DatabaseReference) {
        val mStorage = FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)
        var urlPhoto = ""
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
                val childUpdate = hashMapOf<String, Any>()
                childUpdate["fotoPerfil"] = urlPhoto
                myRef.updateChildren(childUpdate)


            }
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
                val usuario = Usuario(
                    id,
                    nombre,
                    telefono,
                    genero,
                    urlPhoto
                )
                myRef.child(id).setValue(usuario)
                /////////////
            } else {
                // Handle failures
                // ...
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }


}