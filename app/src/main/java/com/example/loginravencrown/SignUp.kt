package com.example.loginravencrown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    private val DB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Setup
        setup()
    }

    private fun setup() {

        title = "Registro"

        val botonRegistroUsuario: Button = findViewById(R.id.botonRegister)
        botonRegistroUsuario.setOnClickListener{

            val userIngreso: EditText = findViewById(R.id.ingresoUsuario)
            val emailIngreso: EditText = findViewById(R.id.ingresoEmail)
            val passwordIngreso: EditText = findViewById(R.id.ingresoPassword)
            if  (emailIngreso.text.isNotEmpty() && passwordIngreso.text.isNotEmpty() && userIngreso.text.isNotEmpty()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailIngreso.text.toString(),passwordIngreso.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        showInicio(userIngreso.text.toString(),it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else {
                        showAlert()
                    }
                }
            }
        }

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInicio(user: String, email: String, provider: ProviderType) {
        val llamadaInicio = Intent(this, Inicio::class.java).apply {
            putExtra("email",email)
            putExtra("provider", provider.name)
        }
        DB.collection("users").document(email).set(hashMapOf("provider" to provider,"user" to user.toString(),"address" to email.toString()))
        startActivity(llamadaInicio)
    }

}