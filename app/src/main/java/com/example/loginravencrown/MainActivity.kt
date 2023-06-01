package com.example.loginravencrown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup
        setup()
    }

    private fun setup() {

        title = "Autentificación"

        val botonRegistroUsuario: Button = findViewById(R.id.botonRegister)
        botonRegistroUsuario.setOnClickListener {
            val  llamadaPantallaRegistro = Intent(this, SignUp::class.java)
            startActivity(llamadaPantallaRegistro)
        }

        val botonLoginUsuario: Button = findViewById(R.id.botonLogin)
        botonLoginUsuario.setOnClickListener{

            val emailIngreso: EditText = findViewById(R.id.ingresoEmail)
            val passwordIngreso: EditText = findViewById(R.id.ingresoPassword)
            if  (emailIngreso.text.isNotEmpty() && passwordIngreso.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailIngreso.text.toString(),passwordIngreso.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        showInicio(it.result?.user?.email ?: "", ProviderType.BASIC)
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

    private fun showInicio(email: String, provider: ProviderType) {
        val llamadaInicio = Intent(this, Inicio::class.java).apply {
            putExtra("email",email)
            putExtra("provider", provider.name)
        }
        startActivity(llamadaInicio)
    }
}