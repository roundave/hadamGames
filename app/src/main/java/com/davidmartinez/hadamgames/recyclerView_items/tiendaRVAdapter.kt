package com.davidmartinez.hadamgames.recyclerView_items

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.tiendaRemote
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rv_tienda.view.*

class tiendaRVAdapter (

    var tiendaList: ArrayList<tiendaRemote>
) : RecyclerView.Adapter<tiendaRVAdapter.tiendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tiendaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tienda, parent, false)
        return tiendaViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = tiendaList.size


    override fun onBindViewHolder(holder: tiendaViewHolder, position: Int) {
        val tienda = tiendaList[position]
        holder.bindTienda(tienda)

    }


    class tiendaViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindTienda(tienda: tiendaRemote) {
            itemView.tv_nombre_tienda.text = tienda.nombre
            itemView.tv_genero_tienda.text = tienda.genero
            itemView.tv_precio_final.text = tienda.precio.toString()
            //itemView.cl_item_novedades.background=novedades.color
            if (tienda.imagen.isNotEmpty()){
                val url=tienda.imagen

                Picasso.get().load(url).placeholder(R.drawable.ic_player_placeholder).error(R.drawable.ic_error).into(itemView.iv_juego_tienda);
                Log.d("direccion", itemView.iv_juego_tienda.toString())
            }

        }
    }



}