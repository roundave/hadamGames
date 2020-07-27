package com.davidmartinez.hadamgames.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.davidmartinez.hadamgames.MainActivity
import com.davidmartinez.hadamgames.R
import com.davidmartinez.hadamgames.ui.login.loginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_fr_menu.*


class fr_menu : Fragment() {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance() // firebase
    val User: FirebaseUser? = mAuth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_menu, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_cerrar_sesion.setOnClickListener {
            mAuth.signOut();
            startActivity(Intent(requireContext(), loginActivity::class.java))
            requireActivity().finish()
        }
    }


}