package com.ktzy.notetaker

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.BaseColumns
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class NoteRepository(context: Context) : NoteDao {
    private var dbHelper: DBHelper = DBHelper(context)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNotes(): List<Note> {
        val db = dbHelper.readableDatabase

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${NoteEntry.COLUMN_NAME_DATE_CREATED} DESC"

        val cursor = db.query(
            NoteEntry.TABLE_NAME,   // The table to query
            null,          // The array of columns to return
            null,          // The columns for WHERE clause
            null,      // The values for WHERE clause
            null,          // don't group the rows
            null,           // don't filter by row groups
            sortOrder               // The sort order
        )

        var notes = mutableListOf<Note>()

        with (cursor){
            while (moveToNext()){
                val noteId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val noteTitle = getString(getColumnIndex(NoteEntry.COLUMN_NAME_TITLE))
                val noteContent = getString(getColumnIndex(NoteEntry.COLUMN_NAME_CONTENT))
                var noteDateCreated = getString(getColumnIndex(NoteEntry.COLUMN_NAME_DATE_CREATED))
                noteDateCreated = noteDateCreated.replace(' ','T')
                var note = Note(noteId, noteTitle, noteContent.toString(), LocalDateTime.parse(noteDateCreated))
                println(note)
                notes.add(note)
            }
        }
        dbHelper.close()
        return notes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNoteByID(id: Int): Note? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            NoteEntry.TABLE_NAME,
            null,
            "${BaseColumns._ID} = ?",
             arrayOf(id.toString()),
            null,
            null,
            null
        )
        lateinit var note:Note
        with(cursor){
            if(cursor.moveToFirst()) {
                val noteId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val noteTitle = getString(getColumnIndex(NoteEntry.COLUMN_NAME_TITLE))
                val noteContent = getString(getColumnIndex(NoteEntry.COLUMN_NAME_CONTENT))
                var noteDateCreated = getString(getColumnIndex(NoteEntry.COLUMN_NAME_DATE_CREATED))
                noteDateCreated = noteDateCreated.replace(' ', 'T')
                note = Note(
                    noteId,
                    noteTitle,
                    noteContent.toString(),
                    LocalDateTime.parse(noteDateCreated)
                )
                db.close()
                return note
            }
        }
        return null
    }

    override fun createNote(note: Note) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NoteEntry.COLUMN_NAME_TITLE, note.title)
            put(NoteEntry.COLUMN_NAME_CONTENT, note.content)
        }
        db?.insert(NoteEntry.TABLE_NAME, null, values)
        dbHelper.close()
    }

    override fun editNote(note: Note) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NoteEntry.COLUMN_NAME_TITLE, note.title)
            put(NoteEntry.COLUMN_NAME_CONTENT, note.content)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(note.id.toString())
        db.update(
            NoteEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    override fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }
}