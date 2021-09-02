package com.myerasmus.ui.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myerasmus.R
import com.myerasmus.data.model.DiaryFromList

class DiaryAdapter(
    private val context: Context?,
    private val diaryList: MutableList<DiaryFromList>
) : RecyclerView.Adapter<DiaryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_diary, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.MyViewHolder, position: Int) {
        val diaryEntry : DiaryFromList = diaryList[position]
        holder.title.text = diaryEntry.title
        holder.date.text = diaryEntry.date
        holder.description.text = diaryEntry.description
        //holder.image.text = diaryEntry.image
    }

    override fun getItemCount(): Int {
        return if(diaryList.size > 0){
            diaryList.size
        }
        else {
            0
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        var date: TextView = itemView.findViewById(R.id.tvDate)
        val description: TextView = itemView.findViewById(R.id.rvDescription)
        val image: ImageView = itemView.findViewById(R.id.tvImage)
    }

}