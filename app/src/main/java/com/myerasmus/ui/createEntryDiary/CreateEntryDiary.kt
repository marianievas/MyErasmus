package com.myerasmus.ui.createEntryDiary

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.myerasmus.MainActivity
import com.myerasmus.R
import com.myerasmus.common.Constants
import kotlinx.android.synthetic.main.new_entry_diary.*
import kotlinx.android.synthetic.main.new_entry_diary.view.*
import java.util.*


class CreateEntryDiary: AppCompatActivity()  {

    private lateinit var titleEntry: EditText
    private lateinit var description: EditText
    private lateinit var btnAdd: Button
    private lateinit var datePicker: Button
    private lateinit var date: TextView
    private lateinit var mainPhoto: ImageView

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

        Glide.with(this)
                .load(Constants().URL_STORAGE + "upload/userimageget/")
                .placeholder(R.drawable.friends).centerCrop().circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .into(imageViewPic)

        mainPhoto.setOnClickListener{
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                startActivityForResult(pickPhoto, Constants().SELECT_PHOTO_GALLERY)
                            }
                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied) {
                                // permission is denied permenantly, navigate user to app settings
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                                permissions: List<PermissionRequest>,
                                token: PermissionToken
                        ) {
                            token.continuePermissionRequest()
                        }
                    })
                    .onSameThread()
                    .check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants().PICK_PHOTO_FOR_AVATAR && resultCode == AppCompatActivity.RESULT_OK) {
            val photoPath = Constants().URL_STORAGE + "/pics"
            Glide.with(this).load(photoPath).centerCrop().circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .into(mainPhoto)
           /* if (data != null) {
                val imageSelected = data.data
                val filepathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor? = requireContext().contentResolver.query(
                        imageSelected!!,
                        filepathColumn,
                        null,
                        null,
                        null
                )
                if (cursor != null) {
                    cursor.moveToFirst()
                    val imageIndex: Int = cursor.getColumnIndex(filepathColumn[0])
                    val photoPath: String = cursor.getString(imageIndex)
                            Glide.with(this).load(photoPath).centerCrop().circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .into(mainPhoto)
                            cursor.close()
                }
            }*/
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