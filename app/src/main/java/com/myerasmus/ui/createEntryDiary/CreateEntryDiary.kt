package com.myerasmus.ui.createEntryDiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.with
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myerasmus.MainActivity
import com.myerasmus.R
import kotlinx.android.synthetic.main.new_entry_diary.*
import kotlinx.android.synthetic.main.new_entry_diary.view.*
import java.text.SimpleDateFormat
import java.util.*


class CreateEntryDiary: AppCompatActivity()  {

    private lateinit var titleEntry: EditText
    private lateinit var description: EditText
    private lateinit var btnAdd: Button
    private lateinit var datePicker: Button
    private lateinit var date: TextView
    private lateinit var mainPhoto: ImageView

    var imageUri: Uri? = null
    var storageReference: StorageReference? = null
    var progressDialog: ProgressDialog? = null

    private var db = FirebaseFirestore.getInstance()

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
        mainPhoto = findViewById(R.id.imageViewPic)


        btnAdd.setOnClickListener {
            newEntry()
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

        mainPhoto.setOnClickListener {
            selectImage()
        }
    }

    private fun uploadImage() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Uploading File....")
        progressDialog!!.show()
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName: String = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        imageUri?.let {
            storageReference!!.putFile(it)
                    .addOnSuccessListener {
                        mainPhoto.setImageURI(null)
                        Toast.makeText(this@CreateEntryDiary, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                    }.addOnFailureListener {
                        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                        Toast.makeText(this@CreateEntryDiary, "Failed to Upload", Toast.LENGTH_SHORT).show()
                    }
            with(applicationContext).load(it).into(mainPhoto);
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            imageUri = data.data
            mainPhoto.setImageURI(imageUri)
            uploadImage()
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

    private fun newEntry(){
        val mainIntent: Intent = Intent(this, MainActivity::class.java).apply {
            // putExtra("titleEnt", titleEntry)
            // putExtra("descrEnt", description)
            // putExtra("provider", ProviderType.BASIC.name)

            //putExtra("date", date)
        }

        //guardar entrada del diari per l'usuari
        db.collection("users").document(email).set(
                hashMapOf("address" to email, "username" to username))

        startActivity(mainIntent)
        finish()
    }



}