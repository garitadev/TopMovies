package com.isc.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.isc.topmovies.databinding.ActivityCambiarPasswordBinding
import com.isc.topmovies.databinding.ActivityEmailAuthBinding

class CambiarPasswordActivity : AppCompatActivity() {
    //variables de los componentes
    private lateinit var binding: ActivityCambiarPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.senEmailAppCompatButton.setOnClickListener{
            //Se obtiene el correo al que se enviaran los pasos para restablecer el password
            val emailAddress = binding.emailEditText.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->

                if (task.isSuccessful){
                    val intent = Intent(this, SingInActivity::class.java)
                    this.startActivity(intent)
                    Toast.makeText(baseContext, "Se ha enviado un correo con los pasos a seguir para cambiar su contraseña",
                        Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(baseContext, "El email ingresado no es válido",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}