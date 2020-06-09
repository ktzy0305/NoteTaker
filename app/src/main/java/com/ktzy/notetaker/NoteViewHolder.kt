package com.ktzy.notetaker

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class NoteViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.note_item, parent, false)) {
    private var title: TextView? = null
    private var content: TextView? = null
    private var dateCreated: TextView? = null

    init{
        title = itemView.findViewById(R.id.txtTitle)
        content = itemView.findViewById(R.id.txtContent)
        dateCreated = itemView.findViewById(R.id.txtDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(note: Note){
        title?.text = if (note.title == "") "Untitled Note" else note.title
        content?.text = note.content
        dateCreated?.text = note.dateCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")).toString()

        itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(itemView.context, EditNote::class.java)
            intent.putExtra("NoteID", note.id.toString())
            itemView.context.startActivity(intent)
        })
    }
}