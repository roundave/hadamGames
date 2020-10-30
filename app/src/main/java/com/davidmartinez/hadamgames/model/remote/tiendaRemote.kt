package com.davidmartinez.hadamgames.model.remote

import java.io.Serializable

class tiendaRemote(
    val id: String?="",
    val nombre: String="",
    val descripcion: String="",
    val desarrolador: String="",
    val fecha: String="",
    val imagen:String="",
    val genero:String="",
    val precio: Int =0
    //val color: Drawable
    ):Serializable

