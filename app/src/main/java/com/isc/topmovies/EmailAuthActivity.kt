package com.isc.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.isc.topmovies.databinding.ActivityEmailAuthBinding
import com.isc.topmovies.databinding.ActivityMainBinding

class EmailAuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityEmailAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se inicializan las variables
        binding = ActivityEmailAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val usuario = auth.currentUser

        binding.veficateEmailAppCompatButton.setOnClickListener{
            val profileUpdates = userProfileChangeRequest { }
            usuario!!.updateProfile(profileUpdates).addOnCompleteListener{ task ->

                if (task.isSuccessful){//si el estado del usuario se actualizó se verifica el correo
                    if (usuario.isEmailVerified){
                        reload()
                    }else{
                        Toast.makeText(baseContext, "Por favor verifica tu correo",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
        binding.signOutImageView.setOnClickListener{
            signOut()
        }
    }

    //Para que una vez se logue un usuario no tenga que volver a hacerlo
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                reload()
            }else{
                sendEmailVerification()
            }
        }
    }

    //funcion para enviar un email
    private fun sendEmailVerification(){
        val usuario = auth.currentUser
        usuario!!.sendEmailVerification().addOnCompleteListener {task->
            if (task.isSuccessful){
                Toast.makeText(baseContext, "Por favor confirme su email mediante el correo de verificacion enviado",
                    Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, SingInActivity::class.java)
        startActivity(intent)
    }
}