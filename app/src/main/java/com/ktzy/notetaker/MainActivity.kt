package com.ktzy.notetaker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var noteRepository: NoteRepository
    private lateinit var addBtn: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        // Note Repository
        noteRepository = NoteRepository(applicationContext)
        // Layout Components
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
        val notes = noteRepository.getNotes()
        notesRecyclerView.layoutManager = linearLayoutManager
        val noteAdapter = NoteAdapter(notes)
        notesRecyclerView.adapter = noteAdapter
        noteAdapter.notifyDataSetChanged()
    }
}