package com.myerasmus.data.model

import android.provider.MediaStore
import java.util.*

data class DiaryFromList (
        val title: String,
        val description: String,
        val date: Date
       // val song: MediaStore.Audio.Playlists
)