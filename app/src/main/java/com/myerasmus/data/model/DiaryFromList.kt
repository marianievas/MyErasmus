package com.myerasmus.data.model

import android.widget.ImageView

data class DiaryFromList(
        val title: String,
        val description: String,
        val date: String,
        val image: String,
        val mood: String
       // val song: MediaStore.Audio.Playlists
)