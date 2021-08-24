package com.myerasmus

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity()  {
    private lateinit var layout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val buttonCreate = findViewById<FloatingActionButton>(R.id.buttonCreateActivity)

        layout = findViewById(R.id.container)

        if (intent.extras != null && intent.extras!!.getString("achievement_list") != null) { // si tenim intent.extras (és a dir, venim de createActivity i ens ha passat achievements)
            Log.d("mainActvty Achievements", "MainActivity: tinc intent.extra. vaig a snackbar")

            val arguments = intent.extras
        }
    }
}