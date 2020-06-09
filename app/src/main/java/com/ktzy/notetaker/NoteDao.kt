package com.ktzy.notetaker

interface NoteDao {
    fun getNotes():List<Note>
    fun getNoteByID(id: Int): Note?
    fun createNote(note: Note)
    fun editNote(note: Note)
    fun deleteNote(note: Note)
}