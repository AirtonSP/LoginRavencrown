package com.example.loginravencrown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType {
    BASIC
}

class Inicio : AppCompatActivity() {

    private val DB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {

        title = "Inicio"

        val nombreUser: TextView = findViewById(R.id.nombreUsuario)
        DB.collection("users").document(email).get().addOnSuccessListener {
            nombreUser.setText(it.get("user") as String?)
        }


        val botonAjustesUsuario: ImageView = findViewById(R.id.icono3)
        botonAjustesUsuario.setOnClickListener {
            val  llamadaPantallaPerfil = Intent(this, Perfil::class.java)
            startActivity(llamadaPantallaPerfil)
        }


        val cierreSesion: ImageView = findViewById(R.id.icono4)
        cierreSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }


    }
}