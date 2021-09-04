package com.myerasmus.ui.createEntryDiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.myerasmus.MainActivity
import com.myerasmus.R
import kotlinx.android.synthetic.main.new_entry_diary.*
import kotlinx.android.synthetic.main.new_entry_diary.view.*
import java.util.*


class CreateEntryDiary: AppCompatActivity()  {

    private lateinit var titleEntry: EditText
    private lateinit var description: EditText
    private lateinit var btnAdd: Button
    private lateinit var datePicker: Button
    private lateinit var date: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entry_diary)

        this.title = "New entry"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setCustomAdapter()

        titleEntry = findViewById(R.id.activity_title)
        description = findViewById(R.id.about_the_activity)
        datePicker = findViewById(R.id.btn_pickDate)
        date = findViewById(R.id.tvDate)
        btnAdd = findViewById(R.id.btn_create)


        btnAdd.setOnClickListener {
            val mainIntent: Intent = Intent(this, MainActivity::class.java).apply {
                // putExtra("titleEnt", titleEntry)
                // putExtra("descrEnt", description)
                // putExtra("provider", ProviderType.BASIC.name)

                //putExtra("date", date)
            }
            startActivity(mainIntent)
            finish()
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datePicker.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, _, dayOfMonth ->
                // Display Selected date in TextView
                date.text = "$dayOfMonth-$month-$year"
            }, year, month, day)
            dpd.show()
        }
    }


    private fun setCustomAdapter() {
        val adapter = EmoticonArrayAdapter(this, Emoticons.list!!)
        mood_spinner.adapter = adapter
    }

    //no funciona no sé per què
    private fun initView() {
        val view = layoutInflater.inflate(R.layout.new_entry_diary, null)
        titleEntry = view.activity_title
        description = view.about_the_activity
        btnAdd = view.btn_create
        datePicker = view.btn_pickDate
        date = view.tvDate
    }



}