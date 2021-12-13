package com.isc.topmovies

import android.content.Intent
import android.content.pm.SigningInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.isc.topmovies.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se inicializan las variables declaradas
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.signUpButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.repeatPasswordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()
            val comprobarPassword = Pattern.compile("^"+
                    "(?=.*[-@#$%^&+=])"
                    +".{6,}"
                    +"$")


            //Con !Patterns podemos verificar que el formato del email sea válido
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(baseContext, "Ingrese un email válido",
                    Toast.LENGTH_SHORT).show()

            }else if(email.isEmpty() ||!comprobarPassword.matcher(password).matches()){
                Toast.makeText(baseContext, "La contraseña debe tener al menos 6 caracteres y una caracter especial",
                    Toast.LENGTH_SHORT).show()

            }else if(password != repeatPassword){
                Toast.makeText(baseContext, "Confirma la contraseña",
                    Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email, password)
                Toast.makeText(baseContext, "Cuenta creada exitosamente!",
                    Toast.LENGTH_SHORT).show()
            }
        }
        binding.backImageView.setOnClickListener{
            val intent = Intent(this,SingInActivity::class.java)
            startActivity(intent)
        }
    }


    //Funcion para el registro de usuarios - para hacerlo requirimos de email and password
    private fun createAccount(email : String, password : String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
}