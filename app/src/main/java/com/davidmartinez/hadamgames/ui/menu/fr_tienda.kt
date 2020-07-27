package com.davidmartinez.hadamgames.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.OverlapDecoration
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.recyclerView_items.tiendaRVAdapter
import com.davidmartinez.hadamgames.model.remote.tiendaRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_fr_evento.*
import kotlinx.android.synthetic.main.fragment_fr_tienda.*


class fr_tienda : Fragment() {

    val tiendaList: MutableList<tiendaRemote> = mutableListOf()
    private lateinit var tiendaAdapter: tiendaRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_tienda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarTienda()

        rv_tienda.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,// importante para la forma en la que se crea el recyvler
            true
        )
        rv_tienda.setHasFixedSize(true)
        tiendaAdapter = tiendaRVAdapter(tiendaList as ArrayList<tiendaRemote>)
        rv_tienda.adapter = tiendaAdapter
    }

    private fun cargarTienda() {

        val mStorageRef=FirebaseStorage.getInstance().getReference("portadas")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("juegos")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val tienda = datasnapshot.getValue(tiendaRemote::class.java)
                    tiendaList.add(tienda!!)

                }
                tiendaAdapter.notifyDataSetChanged()
                // Log.d("data", snapshot.toString())

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }




}