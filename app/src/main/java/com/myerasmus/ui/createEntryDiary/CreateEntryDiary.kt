package com.myerasmus.ui.createEntryDiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.myerasmus.MainActivity
import com.myerasmus.ProviderType
import com.myerasmus.R
import kotlinx.android.synthetic.main.new_entry_diary.*
import java.util.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.new_entry_diary.view.*


class CreateEntryDiary: AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    private lateinit var titleEntry: EditText
    private lateinit var description: EditText
    private lateinit var btnAdd: Button

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    private lateinit var datePicker: Button
    private lateinit var date: TextView

    var DATE_DIALOG = 1
    var dateListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyErasmus)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entry_diary)

        this.title = "New entry"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setCustomAdapter()

        initView()

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

        setCurrentDateOnView()
        addListenerOnButton()

    }


    private fun setCustomAdapter() {
        val adapter = EmoticonArrayAdapter(this, Emoticons.list!!)
        mood_spinner.adapter = adapter
    }

    private fun initView() {
        val view = layoutInflater.inflate(R.layout.new_entry_diary, null)
        titleEntry = view.activity_title
        description = view.about_the_activity
        btnAdd = view.btn_create
        datePicker = view.btn_pickDate
        date = view.tvDate
    }


    /**
     * This function represents the current time using current locale and timezone
     */
    @SuppressLint("SetTextI18n")
    private fun setCurrentDateOnView() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.MINUTE)
        minute = cal.get(Calendar.MINUTE)

        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.YEAR, year)

        date.text = "$savedDay-$savedMonth-$savedYear\n at $savedHour:$savedMinute h"
    }


    /**
     * This function let the user pick a date where the activity created will take place
     */
    private fun addListenerOnButton() {

        datePicker.setOnClickListener {
            showDialog(DATE_DIALOG)
            val dialogDate = DatePickerDialog(this, this, this.year, this.month, this.day)
            dialogDate.show()
            dialogDate.datePicker.minDate = System.currentTimeMillis()
        }
    }


    /**
     * This function is called every time the user changes the date picked
     */
    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        // when dialog box is closed, below method will be called.
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        TimePickerDialog(this, this, hour, minute, true).show()
    }


    /**
     * This function is called when the user is done setting a new time and the dialog has closed
     */
    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        date.text = "$savedDay-$savedMonth-$savedYear\n at $savedHour:$savedMinute"
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            DATE_DIALOG -> {
                return DatePickerDialog(
                        this,
                        dateListener,
                        year,
                        month,
                        day
                )
            }
        }
        return null
    }

}