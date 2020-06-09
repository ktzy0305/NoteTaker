package com.ktzy.notetaker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var noteRepository: NoteRepository
    private lateinit var addBtn: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        dbHelper = DBHelper(applicationContext)
        noteRepository = NoteRepository(applicationContext)

        addBtn = findViewById(R.id.fabAdd)
        notesRecyclerView = findViewById(R.id.rvNotes)
        linearLayoutManager = LinearLayoutManager(this)

        addBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, AddNote::class.java)
            startActivity(intent)
        })

        getAllNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_action_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllNotes(){
        var notes = noteRepository.getNotes()
        println(notes)
        notesRecyclerView.layoutManager = linearLayoutManager
        val noteAdapter = NoteAdapter(notes)
        notesRecyclerView.adapter = noteAdapter
        noteAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }


}