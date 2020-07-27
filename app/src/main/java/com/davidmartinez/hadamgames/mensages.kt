package com.davidmartinez.hadamgames

import android.content.Context
import android.widget.Toast


fun showMessage(context: Context, mensaje:String){

    Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show()
}