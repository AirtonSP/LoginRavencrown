package com.example.loginravencrown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Perfil : AppCompatActivity() {

    private val DB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val bundle = intent.extras
        val email = bundle?.getString("email")

        // Setup
        setup(email ?: "")
    }

    private fun setup(email: String) {

        title = "Perfil"

        val nombreUser: TextView = findViewById(R.id.textoPerfil2)
        val nombreAddress: TextView = findViewById(R.id.textoPerfil4)
        val nombreProvider: TextView = findViewById(R.id.textoPerfil6)
        DB.collection("users").document(email).get().addOnSuccessListener {
            //nombreUser.setText(it.get("user") as String?)
            //nombreAddress.setText(it.get("address") as String)
            //nombreProvider.setText(it.get("provider") as String?)
        }

    }

}