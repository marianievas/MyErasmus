package com.myerasmus

import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.*
import com.myerasmus.data.model.DiaryFromList
import com.myerasmus.ui.createEntryDiary.CreateEntryDiary
import com.myerasmus.ui.diary.DiaryAdapter

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity()  {
    private lateinit var layout: ConstraintLayout

    private lateinit var logOutBtn : Button

    private lateinit var recycleView: RecyclerView
    private var diaryArrayList : MutableList<DiaryFromList> = ArrayList()
    private lateinit var adapter : DiaryAdapter
    private lateinit var db : FirebaseFirestore

    private lateinit var newPageDiary : FloatingActionButton

    private lateinit var navView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //logOutBtn = findViewById(R.id.logout)

        navView = findViewById(R.id.nav_view)
        layout = findViewById(R.id.container)

        newPageDiary = findViewById(R.id.newPage)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val username: String? = bundle?.getString("username")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        recycleView = findViewById(R.id.recyclerView)
       // recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true)

        diaryArrayList.add(DiaryFromList("Day in Berlin", "This day has been fantastic so far. We have visited loads of places and we have met new people.","2022-04-23","https://www.publicdomainpictures.net/pictures/270000/nahled/friends-in-the-park.jpg"))
        diaryArrayList.add(DiaryFromList("First day of classes", "So interesting!!!!!","2021-10-19","https://upload.wikimedia.org/wikipedia/commons/6/68/050322-tumuenchen-parabeln.jpg"))
        diaryArrayList.add(DiaryFromList("Englischer Garten + Volleyball match", "So much fun. Can't wait to repeat the plan:)","2021-10-10","https://live.staticflickr.com/2940/14678882912_b014edefa0_b.jpg"))

        adapter = DiaryAdapter(this, diaryArrayList)
        recycleView.adapter = adapter

        newPageDiary.setOnClickListener{
            val int = Intent(this, CreateEntryDiary::class.java)
            startActivity(int)
            //finish()
        }

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

            adapter.notifyDataSetChanged()
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