package com.myerasmus.ui.createEntryDiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.myerasmus.R
import kotlinx.android.synthetic.main.my_dropdown_emoticons.view.*


class EmoticonArrayAdapter(context: Context, emoticonList: List<Emoticon>) : ArrayAdapter<Emoticon>(context, 0, emoticonList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{
        val emot = getItem(position)

        val view = LayoutInflater.from(context).inflate(R.layout.my_dropdown_emoticons, parent, false)
        view.image.setImageResource(emot!!.image)
      //  view.name.text = emot.name

        return view
    }
}