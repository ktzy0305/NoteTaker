package com.ktzy.notetaker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createNoteTable = "CREATE TABLE ${NoteEntry.TABLE_NAME} (" +
                              "${BaseColumns._ID} INTEGER PRIMARY KEY" +
                              "${NoteEntry.COLUMN_NAME_TITLE} TEXT" +
                              "${NoteEntry.COLUMN_NAME_CONTENT} TEXT" +
                              "${NoteEntry.COLUMN_NAME_DATE} TEXT DEFAULT CURRENT_TIMESTAMP";
        db.execSQL(createNoteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        val deleteNoteTable = "DROP TABLE IF EXISTS ${NoteEntry.TABLE_NAME}"
        db.execSQL(deleteNoteTable)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "NoteTaker.db"
    }
}