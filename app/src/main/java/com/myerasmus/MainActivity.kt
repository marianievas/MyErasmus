package com.myerasmus

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import com.myerasmus.data.model.DiaryFromList
import com.myerasmus.ui.diary.DiaryAdapter

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity()  {
    private lateinit var layout: ConstraintLayout

    private lateinit var logOutBtn : Button

    private lateinit var recycleView: RecyclerView
    private lateinit var diaryArrayList : ArrayList<DiaryFromList>
    private lateinit var myAdapter : DiaryAdapter
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //logOutBtn = findViewById(R.id.logout)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        layout = findViewById(R.id.container)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val username: String? = bundle?.getString("username")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")



        recycleView = findViewById(R.id.recyclerView)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true)

        diaryArrayList = arrayListOf()
        myAdapter = DiaryAdapter(diaryArrayList)
        eventChangeListener()

    }

    private fun eventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("My diary").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore error", error.message.toString())
            }
            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    diaryArrayList.add(dc.document.toObject(DiaryFromList::class.java))
                }
            }

            myAdapter.notifyDataSetChanged()
        }
    }


    private fun setup(email: String, provider: String){
        title = "My Erasmus"
      /*  logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}