package com.davidmartinez.hadamgames.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.novedadesRemote
import com.davidmartinez.hadamgames.recyclerView_items.novedadesRVAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_novedades_fragment.*


class fr_novedades : Fragment() {

    val novedadesList: MutableList<novedadesRemote> = mutableListOf()
    private lateinit var novedadesAdapter: novedadesRVAdapter

    var ingreso = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_novedades_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarNovedades()

        rv_novedades.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,// importante para la forma en la que se crea el recyvler
            true
        )

        rv_novedades.setHasFixedSize(true)
        novedadesAdapter =
            novedadesRVAdapter(novedadesList as ArrayList<novedadesRemote>)
        rv_novedades.adapter = novedadesAdapter
    }

        private fun cargarNovedades() {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("eventos")
            val postListener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnapshot: DataSnapshot in snapshot.children) {
                        val novedades = datasnapshot.getValue(novedadesRemote::class.java)
                        novedadesList.add(novedades!!)

                    }
                    novedadesAdapter.notifyDataSetChanged()
                    // Log.d("data", snapshot.toString())

                }

            }
            myRef.addListenerForSingleValueEvent(postListener)

        }


    }

