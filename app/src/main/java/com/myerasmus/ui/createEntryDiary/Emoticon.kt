package com.myerasmus.ui.createEntryDiary

import com.myerasmus.R

data class Emoticon(val image: Int, val name: String)

object Emoticons {
    private val images = intArrayOf(
            R.drawable.e1,
            R.drawable.e1,
            R.drawable.e2,
            R.drawable.e3,
            R.drawable.e4,
            R.drawable.e5,
            R.drawable.e6,
            R.drawable.e7,
            R.drawable.e8,
            R.drawable.e9,
            R.drawable.e10,
            R.drawable.e11,
            R.drawable.e12,
            R.drawable.e13,
            R.drawable.e14,
            R.drawable.e15,
            R.drawable.e16,
            R.drawable.e17,
            R.drawable.e18,
            R.drawable.e19,
            R.drawable.e20,
            R.drawable.e21,
            R.drawable.e22,
            R.drawable.e23,
            R.drawable.e25,
            R.drawable.e26,
            R.drawable.e27,
            R.drawable.e28,
            R.drawable.e29,
            R.drawable.e30,
            R.drawable.e31,
            R.drawable.e32,
            R.drawable.e33,
    )

    private val emoticons = arrayOf(
            "What's your mood?",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30",
            "31",
            "32",
            "33",
    )

    var list: ArrayList<Emoticon>? = null
        get() {
            if (field != null) return field
            field = ArrayList()
            for (i in images.indices) {
                val imageId = images[i]
                val countryName = emoticons[i]

                val emoticon = Emoticon(imageId, countryName)
                field!!.add(emoticon)
            }
            return field
        }
}