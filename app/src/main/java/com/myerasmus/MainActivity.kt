package com.myerasmus

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity()  {
    private lateinit var layout: ConstraintLayout

    private val logOutBtn = findViewById<Button>(R.id.logout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        layout = findViewById(R.id.container)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val username: String? = bundle?.getString("username")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String){
        title = "Home Screen"
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}