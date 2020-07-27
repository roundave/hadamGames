package com.davidmartinez.hadamgames.recyclerView_items
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.novedadesRemote
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rv_novedades.view.*

class novedadesRVAdapter (
    var novedadesList: ArrayList<novedadesRemote>
) : RecyclerView.Adapter<novedadesRVAdapter.novedadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): novedadesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_novedades, parent, false)
        return novedadesViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = novedadesList.size


    override fun onBindViewHolder(holder: novedadesViewHolder, position: Int) {
        val novedades = novedadesList[position]
        holder.bindNovedad(novedades)

    }


    class novedadesViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindNovedad(novedades: novedadesRemote) {
            itemView.tv_fecha_novedades.text = novedades.fecha
            itemView.tv_nombre_novedades.text = novedades.nombre
            itemView.tv_organizador_novedades.text = novedades.organizador
            //itemView.cl_item_novedades.background=novedades.color
            if (!novedades.urlForo.isNullOrEmpty())
                Picasso.get().load(novedades.urlForo).into(itemView.iv_organizador_novedades)

        }
    }}

