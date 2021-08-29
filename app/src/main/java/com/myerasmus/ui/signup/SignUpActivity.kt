package com.myerasmus.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.myerasmus.MainActivity
import com.myerasmus.ProviderType
import com.myerasmus.R
import com.myerasmus.ui.login.LoginActivity
import org.w3c.dom.Text

class SignUpActivity: AppCompatActivity(), View.OnClickListener{

    private lateinit var signUpBtn : Button
    private lateinit var editTextUsername : EditText
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var goLogin : TextView
    private lateinit var loading : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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

        /*signUpBtn.setOnClickListener {
            Log.d(String.toString(), "AAAAAAAAAAAA")
            loading.visibility = View.VISIBLE
            if (editTextEmail.text.isNotEmpty() && editTextUsername.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                    //if (it.isSuccessful) showHome(it.result?.user?.email ?: "", editTextUsername.text.toString(), ProviderType.BASIC)
                    if (it.isSuccessful) {
                        onBackPressed()
                        // val homeIntent = Intent(this, LoginActivity::class.java)
                        // startActivity(homeIntent)
                    } else showAlert()
                }
            }
        }*/
        signUpBtn.setOnClickListener(this)
        goLogin.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ButtonSignUp->{
                Log.d(String.toString(), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                loading.visibility = View.VISIBLE
                if (editTextEmail.text.isNotEmpty() && editTextUsername.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) onBackPressed()
                    else showAlert()
                    }
                }
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

    private fun showHome(email: String, username: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("username", username)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}