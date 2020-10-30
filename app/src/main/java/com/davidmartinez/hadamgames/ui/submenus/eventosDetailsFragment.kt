package com.davidmartinez.hadamgames.ui.submenus

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.system.Os.close
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.model.remote.eventoRemote
import com.davidmartinez.hadamgames.showMessage
import com.davidmartinez.hadamgames.ui.submenus.eventosDetailsFragmentArgs.Companion.fromBundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_eventos_details.*
import java.io.IOException


class eventosDetailsFragment : Fragment(){

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(context as Activity, Color.parseColor("#B4C55B"))
    }



    private val callback = OnMapReadyCallback { googleMap ->
        val evento=arguments?.getSerializable("hola")
        evento as eventoRemote
        val geocoder=Geocoder(context)
        val latitud=0.0
        val longitud=0.0
        var list:MutableList<Address> = mutableListOf()
        try{
            list=geocoder.getFromLocationName(evento.direccion,1)
        }catch (e:IOException){
            showMessage(requireContext(),"Direccion no encontrada")
        }
            if (list.size!=0){
                val address:Address=list[0]
                val posicion=LatLng(address.latitude,address.longitude)
                googleMap.addMarker(MarkerOptions().position(posicion).title(evento.direccion))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,15f))

            }


        googleMap.uiSettings.isZoomControlsEnabled=true
       //googleMap.setOnPoiClickListener()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos_details, container, false)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val mapFragment = childFragmentManager.findFragmentById(R.id.mv_details) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            val evento=arguments?.getSerializable("hola")
            evento as eventoRemote
                //val safeArgs=eventosDetailsFragment.fromBundle(it)
               // val evento:eventoRemote=safeArgs.evento
                setData(evento)

            bt_back_evento.setOnClickListener{
                activity?.supportFragmentManager?.popBackStack()// revisar
                StatusBarUtil.setColor(context as Activity, Color.parseColor("#FFFFFF"))
            }
            }




    private fun setData(evento:eventoRemote) {
        tv_titulo_eventoDetails.text=evento.nombre
        tv_fechaEventoDetails.text=evento.fecha
        tv_ubicacion_eventoDetails.text=evento.direccion
        tv_precioEventoDetails.text= (" $ ${evento.precio.toString()}")
        tv_organizadorEventoDetails.text=evento.organizador
        tv_descripcionEventoDetails.text=evento.descripcion
    }


}