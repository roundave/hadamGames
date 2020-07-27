package com.davidmartinez.hadamgames.model.remote

import java.io.Serializable

class eventoRemote(
    val id: String? = "",
    val nombre: String = "",
    val descripcion: String = "",
    val organizador: String = "",
    val fecha: String = "",
    val urlForo: String = "",
    val direccion: String = "",
    val precio:Int=0
//val color: Drawable
) : Serializable