package com.ktzy.notetaker

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.security.auth.callback.Callback

class NoteAdapter(private val notes: MutableList<Note>): RecyclerView.Adapter<NoteViewHolder>() {
    private var removedPosition: Int = 0
    private var removedNote: Note? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = notes.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.bind(note)
    }

    fun removeItem(viewHolder: NoteViewHolder){
        removedPosition = viewHolder.adapterPosition
        removedNote = notes[viewHolder.adapterPosition]
        notes.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        var snackbar: Snackbar =  Snackbar.make(viewHolder.itemView, "Note: '${removedNote!!.title}' deleted.", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            notes.add(removedPosition, removedNote!!)
            notifyItemInserted(removedPosition)
        }
        snackbar.addCallback(object: BaseTransientBottomBar.BaseCallback<Snackbar>(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                when(event){
                    Snackbar.Callback.DISMISS_EVENT_TIMEOUT -> {
                        val noteRepository = NoteRepository(viewHolder.itemView.context)
                        noteRepository.deleteNote(removedNote!!)
                        removedNote = null
                    }
                }
                super.onDismissed(transientBottomBar, event)
            }

            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }
        })
        snackbar.show()
    }
}
