package com.ktzy.notetaker

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class AddNote : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_view)
        noteRepository = NoteRepository(applicationContext)
        dbHelper = DBHelper(applicationContext)
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

//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put(NoteEntry.COLUMN_NAME_TITLE, noteTitle.text.toString())
//            put(NoteEntry.COLUMN_NAME_CONTENT, noteContent.text.toString())
//        }
//        db?.insert(NoteEntry.TABLE_NAME, null, values)

        //Redirect to MainPage
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
