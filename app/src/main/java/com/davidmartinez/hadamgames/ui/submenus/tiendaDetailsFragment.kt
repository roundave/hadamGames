package com.davidmartinez.hadamgames.ui.submenus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.sliderRemote
import com.davidmartinez.hadamgames.model.remote.tiendaRemote
import com.davidmartinez.hadamgames.recyclerView_items.sliderRVAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.tienda_details_fragment.*

class tiendaDetailsFragment : Fragment(){
val slideList: MutableList<sliderRemote> = mutableListOf()
private lateinit var sliderAdapter: sliderRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tienda_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val juego=arguments?.getSerializable("hola2")
        juego as tiendaRemote
        setData(juego)
        cargarFotos(juego.id.toString())
        sliderAdapter = sliderRVAdapter(slideList as ArrayList<sliderRemote>,this)
        vp_slider_tienda.adapter = sliderAdapter

        bt_atrasTiendaDetails.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun cargarFotos(id:String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("fotos").child("Cuphead")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val slider = datasnapshot.getValue(sliderRemote::class.java)
                    slideList.add(slider!!)

                }
                sliderAdapter.notifyDataSetChanged()
                // Log.d("data", snapshot.toString())

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }

    private fun setData(tienda:tiendaRemote) {
        tv_nombreTiendaDetails.text=tienda.nombre
        tv_desarrolladorTiendaDetails.text=tienda.desarrolador
        tv_generoTiendaDetails.text=tienda.genero
        tv_descripcionJuegoDestails.text=tienda.descripcion
        tv_precioJuego.setText("$ ${tienda.precio}").toString()
    }

}