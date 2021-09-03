package com.myerasmus.ui.createEntryDiary

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.myerasmus.R
import kotlinx.android.synthetic.main.new_entry_diary.*
import java.util.*


class CreateEntryDiary: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entry_diary)

        this.title = "New entry"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setCustomAdapter()


    }

    private fun setCustomAdapter(){
        val adapter = EmoticonArrayAdapter(this, Emoticons.list!!)
        mood_spinner.adapter = adapter
    }
}