package com.ktzy.notetaker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

    fun bind(note: Note){
        title?.text = note.title
        content?.text = note.content
        dateCreated?.text = note.dateCreated.toString()
    }

}