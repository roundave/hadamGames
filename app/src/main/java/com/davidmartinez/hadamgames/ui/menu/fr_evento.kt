package com.davidmartinez.hadamgames.ui.menu

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.recyclerView_items.eventosRVAdapter
import com.davidmartinez.hadamgames.model.remote.eventoRemote
import com.davidmartinez.hadamgames.model.remote.tiendaRemote
import com.davidmartinez.hadamgames.ui.submenus.eventosDetailsFragment
import com.davidmartinez.hadamgames.ui.submenus.eventosDetailsFragmentArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_fr_evento.*
import kotlinx.android.synthetic.main.fragment_novedades_fragment.*


class fr_evento : Fragment(),eventosRVAdapter.OnItemClickListener{

    val eventoList: MutableList<eventoRemote> = mutableListOf()
    private lateinit var eventoAdapter: eventosRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_evento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarEvento()
        rv_eventos.addItemDecoration(MyItemDecoration())
        rv_eventos.setChildDrawingOrderCallback(BackwardsDrawingOrderCallback())

        rv_eventos.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,// importante para la forma en la que se crea el recyvler
            false
        )
        //rv_eventos.addItemDecoration(OverlapDecoration(),90)

        rv_eventos.setHasFixedSize(true)
        eventoAdapter = eventosRVAdapter(eventoList as ArrayList<eventoRemote>,this)
        rv_eventos.adapter = eventoAdapter
    }



    private fun cargarEvento() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("eventos")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val evento = datasnapshot.getValue(eventoRemote::class.java)
                    eventoList.add(evento!!)

                }
                eventoAdapter.notifyDataSetChanged()
               // Log.d("data", snapshot.toString())

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }

    override fun onItemclick(evento: eventoRemote) {

        val bundle=Bundle()
        val eventosDetailsFragment = eventosDetailsFragment()
        bundle.putSerializable("hola",evento)
        eventosDetailsFragment.arguments=bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.layout_main, eventosDetailsFragment)?.addToBackStack(null)
            ?.commit()
        //val action=fr_eventoDirections.actionNavSlideshowToEventosDetailsFragment(evento)
        //findNavController().navigate(action)

    }

    class MyItemDecoration : RecyclerView.ItemDecoration() {

        private val overlapValue = -250

        override fun getItemOffsets(outRect : Rect, view : View, parent : RecyclerView, state : RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            if (position != 0)
                outRect.set(0, overlapValue, 0,0 )  // args is : left,top,right,bottom
        }
    }

    class BackwardsDrawingOrderCallback : RecyclerView.ChildDrawingOrderCallback {
        override fun onGetChildDrawingOrder(childCount: Int, i: Int) = childCount - i - 1
    }


}
