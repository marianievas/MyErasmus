package com.myerasmus.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.myerasmus.ProviderType
import com.myerasmus.R
import com.myerasmus.ui.login.LoginActivity

class SignUpActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var signUpBtn : Button
    private lateinit var editTextUsername : EditText
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var goLogin : TextView
    private lateinit var loading : ProgressBar

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        signUpBtn = findViewById(R.id.ButtonSignUp)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextEmail = findViewById(R.id.editTextEmail2)
        editTextPassword = findViewById(R.id.editTextPassword2)
        goLogin = findViewById(R.id.textViewHere)
        loading = findViewById(R.id.loading)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Firebase integration completed")
        analytics.logEvent("initScreen", bundle)

        signUpBtn.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (editTextEmail.text.isEmpty() || editTextUsername.text.isEmpty() || editTextPassword.text.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
            else
            {
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                            .show()
                        db.collection("users").document(email).set(
                            hashMapOf("address" to email)
                        )
                        onBackPressed()
                    } else showAlert()
                })
            }
        }
        goLogin.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An error has occurred with the user's authentication")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, username: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("username", username)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}