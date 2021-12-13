package com.isc.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.isc.topmovies.databinding.ActivityMainBinding
import com.isc.topmovies.databinding.ActivitySingInBinding

class SingInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySingInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.signInAppCompatButton.setOnClickListener{
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when{
                mEmail.isEmpty() || mPassword.isEmpty() ->{
                    Toast.makeText(baseContext, "Correo o contraseña no validos",
                        Toast.LENGTH_SHORT).show()
                }else ->{
                    SignIn(mEmail, mPassword)
                }
            }
        }
        //Le asignamos una accion al texto de signup
        binding.signUpTextView.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)

        }
    }
    //Para que una vez se logue un usuario no tenga que volver a hacerlo
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //Validaciones para que no se puedar entrar a la app sin verificar el correo
            if (currentUser.isEmailVerified){
                reload()

            }else{
                val intent = Intent(this,EmailAuthActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun SignIn(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o contraseña no validos",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }



}