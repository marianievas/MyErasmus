package com.myerasmus.common

data class Constants(

        val URL_STORAGE: String = "gs://myerasmus-7dc3a.appspot.com",

    // Preferences
        val PREF_EMAIL: String = "PREF_EMAIL",
        val PREF_USERNAME: String = "PREF_NAME",
        val PREF_PROVIDER: String = "PREF_PROVIDER",
        val PREF_PROVIDER_PASSWORD: String = "PASSWORD",
        val PREF_PROVIDER_GOOGLE: String = "GOOGLE",
        val PREF_UID: String = "PREF_UID",
        val PICK_PHOTO_FOR_AVATAR: Int = 1,
        val SELECT_PHOTO_GALLERY: Int = 1,
    //val PREF_PHOTO: String = "URL_PHOTO",
    //val PICK_PHOTO_FOR_AVATAR: Int = 1,
   // val SELECT_PHOTO_GALLERY: Int = 1,
   // val REQUEST_IMAGE_CAPTURE: Int = 1,
        val PREF_IS_NOT_FIRST_TIME_OPENING_APP: String = "PREF_IS_FIRST_TIME_OPENING_APP",
        var NUM_DIARY: Int = 0,
    //val DARK_MODE: String = "DARK_MODE",
    //val N_EXTERNAL_INVITES: String = "N_EXTERNAL_INVITES"
)
