package com.myerasmus.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myerasmus.MainActivity
import com.myerasmus.ProviderType
import com.myerasmus.R
import com.myerasmus.common.Constants
import com.myerasmus.ui.signup.SignUpActivity


class LoginActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    private lateinit var emailET : EditText
    private lateinit var passwordET : EditText
    private lateinit var loginbtn : Button
    private lateinit var signUpHere : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(200)   //splash
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        isUserLogged()

        emailET = findViewById(R.id.editTextEmail)
        passwordET = findViewById(R.id.editTextPassword)
        loginbtn = findViewById(R.id.buttonLogin)
        signUpHere = findViewById(R.id.textViewHere)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Firebase integration completed")
        analytics.logEvent("initScreen", bundle)

        title = "Authentication"

        signUpHere.setOnClickListener {
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        /*loginbtn.setOnClickListener{
            if (emailET.text.isEmpty() || passwordET.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                val currentUser: FirebaseUser? = auth.currentUser
                if (currentUser != null) {
                    auth.createUserWithEmailAndPassword(
                        emailET.text.toString(),
                        passwordET.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            SharedPreferenceManager.setStringValue(
                                Constants().PREF_EMAIL,
                                emailET.text.toString()
                            )
                            SharedPreferenceManager.setStringValue(
                                Constants().PREF_PROVIDER,
                                Constants().PREF_PROVIDER_GOOGLE
                            )
                            SharedPreferenceManager.setStringValue(
                                Constants().PREF_UID,
                                FirebaseAuth.getInstance().currentUser!!.uid
                            )
                            /* SharedPreferenceManager.setStringValue(
                                Constants().PREF_USERNAME,
                                it.data.username
                            )*/
                            Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()
                            showHome(Constants().PREF_EMAIL ?: "", ProviderType.BASIC)
                        }
                        else showAlert()
                    }
                }
                else Toast.makeText(this, "Please sign up", Toast.LENGTH_LONG).show()
            }
        }*/
        loginbtn.setOnClickListener {
            if (emailET.text.isEmpty() || passwordET.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else {
                val currentUser = Firebase.auth.currentUser!!
                val credential = EmailAuthProvider
                    .getCredential(emailET.text.toString(), passwordET.text.toString())

                currentUser.reauthenticate(credential)
                    .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
                Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()
                showHome(Constants().PREF_EMAIL ?: "", ProviderType.BASIC)
            }
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

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish()
    }

    private fun isUserLogged(){
        val sharedpreferences: SharedPreferences =
            applicationContext.getSharedPreferences("Preferences", 0)
        val login = sharedpreferences.getString("LOGIN", null)


        if (login != null) {
            showHome(Constants().PREF_EMAIL ?: "", ProviderType.BASIC)
            finish()
        }
    }

}