package com.davidmartinez.hadamgames.recyclerView_items

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.sliderRemote
import com.davidmartinez.hadamgames.ui.submenus.tiendaDetailsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.slider_item.view.*

class sliderRVAdapter(
    var sliderList: ArrayList<sliderRemote>,
    tiendaDetailsFragment: tiendaDetailsFragment
    //tiendaDetailsFragment: tiendaDetailsFragment
) : RecyclerView.Adapter<sliderRVAdapter.sliderViewHolder>() {


    override fun getItemCount(): Int=sliderList.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sliderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return sliderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item,parent,false)
        )
    }




    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: sliderViewHolder, position: Int) {
        val slider = sliderList[position]
        holder.bindNovedad(slider)

    }


    class sliderViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("ResourceAsColor")
        fun bindNovedad(slider: sliderRemote) {
                Picasso.get().load(slider.url).into(itemView.iv_slider_item)


        }
    }}
