package com.ktzy.notetaker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class AddNote : AppCompatActivity() {
    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_view)
        // Note Repository
        noteRepository = NoteRepository(applicationContext)
        // Layout Components
        noteTitle = findViewById(R.id.etNoteTitle)
        noteContent = findViewById(R.id.etNoteContent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_action_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_save -> saveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = noteTitle.text.toString()
        val content = noteContent.text.toString()
        val newNote = Note(title, content)
        noteRepository.createNote(newNote)

        //Redirect to MainPage
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    override fun onDestroy() {
        saveNote()
        super.onDestroy()
    }
}
