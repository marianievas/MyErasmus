package com.myerasmus.ui.createEntryDiary

import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.myerasmus.R
import java.util.*

class CreateEntryDiary: AppCompatActivity() {


    private lateinit var titleEntry : EditText
    private lateinit var description : EditText
    private lateinit var mood : Spinner
    private lateinit var datePicked : Date


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entry_diary)

        this.title = "New entry"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleEntry = findViewById(R.id.activity_title)
        description = findViewById(R.id.about_the_activity)
        mood = findViewById(R.id.your_mood_today)

        //mood.selectedItem

    }
}