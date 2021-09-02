package com.myerasmus.ui.createEntryDiary

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.myerasmus.R

class CreateEntryDiary: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.new_entry_diary)

        this.title = "New entry"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}