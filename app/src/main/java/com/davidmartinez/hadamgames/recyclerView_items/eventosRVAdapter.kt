package com.davidmartinez.hadamgames.recyclerView_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.eventoRemote
import com.davidmartinez.hadamgames.ui.submenus.eventosDetailsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rv_eventos.view.*


class eventosRVAdapter(
    var eventosList: ArrayList<eventoRemote>,
    val onItemClickListerner:OnItemClickListener
) : RecyclerView.Adapter<eventosRVAdapter.eventoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eventoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_eventos, parent, false)
        return eventoViewHolder(itemView, onItemClickListerner)
    }

    override fun getItemCount(): Int = eventosList.size


    override fun onBindViewHolder(holder: eventoViewHolder, position: Int) {
        val evento = eventosList[position]
        holder.bindEvento(evento)
        /*holder.itemView.tv_nombre_evento.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val eventosDetailsFragment = eventosDetailsFragment()
                val bundle = Bundle()
                bundle.putSerializable("str", evento)
                bundle.putString("hola", evento.direccion)
                eventosDetailsFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout2, eventosDetailsFragment).addToBackStack(null)
                    .commit()
            }

        })
*/

    }

    class eventoViewHolder(
        itemView: View,
        var onItemClickListerner: OnItemClickListener

    ) : RecyclerView.ViewHolder(itemView) {

        fun bindEvento(evento: eventoRemote) {
            itemView.tv_fecha_eventos.text = evento.fecha
            itemView.tv_nombre_evento.text = evento.nombre
            itemView.tv_organizador_evento.text = evento.organizador
            //itemView.cl_item_evento.background=evento.color
            if (!evento.urlForo.isNullOrEmpty())
                Picasso.get().load(evento.urlForo).into(itemView.iv_organizador_evento)
            itemView.setOnClickListener{
                onItemClickListerner.onItemclick(evento)
            }

        }

    }

    interface OnItemClickListener {
        fun onItemclick(evento: eventoRemote)

    }


}
