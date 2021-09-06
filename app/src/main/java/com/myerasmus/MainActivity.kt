package com.myerasmus

import android.app.AlertDialog
import android.content.ClipData
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.myerasmus.common.Constants
import com.myerasmus.common.SharedPreferenceManager
import com.myerasmus.data.model.DiaryFromList
import com.myerasmus.ui.createEntryDiary.CreateEntryDiary
import com.myerasmus.ui.diary.DiaryAdapter
import com.myerasmus.ui.login.LoginActivity

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity()  {
    private lateinit var layout: ConstraintLayout

    private lateinit var recycleView: RecyclerView
    private var diaryArrayList : MutableList<DiaryFromList> = ArrayList()
    private lateinit var adapter : DiaryAdapter
    private lateinit var db : FirebaseFirestore

    private lateinit var newPageDiary : FloatingActionButton

    private lateinit var navView : BottomNavigationView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        navView = findViewById(R.id.nav_view)
        layout = findViewById(R.id.container)

        newPageDiary = findViewById(R.id.newPage)

        db = FirebaseFirestore.getInstance()

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val username: String? = bundle?.getString("username")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        title = "My Erasmus"

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }


    /**
     * Function called when the user selects an item from the options menu
     * @param item selected
     * @return true if the menu is successfully handled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {

            val logout_dialog = AlertDialog.Builder(this)
            logout_dialog.setTitle(R.string.log_out)
            logout_dialog.setMessage(R.string.dialog_logout_message)
            logout_dialog.setPositiveButton(R.string.ok) { dialog, id ->
                auth.signOut()
                SharedPreferenceManager.deleteData()
                SharedPreferenceManager.setBooleanValue(
                    Constants().PREF_IS_NOT_FIRST_TIME_OPENING_APP,
                    true
                )
                this.run {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            logout_dialog.setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.dismiss()
            }
            logout_dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

}