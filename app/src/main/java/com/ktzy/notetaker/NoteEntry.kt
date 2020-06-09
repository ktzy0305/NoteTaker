package com.ktzy.notetaker

import android.provider.BaseColumns

object NoteEntry : BaseColumns {
    const val TABLE_NAME = "note"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "subtitle"
    const val COLUMN_NAME_DATE_CREATED = "date_created"
}