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
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var addBtn: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    val notes = listOf(
        Note("Shopping List", "3 Tomatoes", LocalDateTime.now()),
        Note("Life Decisions", "Accept Offer", LocalDateTime.now()),
        Note("Third", "Wheelie", LocalDateTime.now())
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        addBtn = findViewById(R.id.fabAdd)
        notesRecyclerView = findViewById(R.id.rvNotes)
        linearLayoutManager = LinearLayoutManager(this)
        notesRecyclerView.layoutManager = linearLayoutManager
        notesRecyclerView.adapter = NoteAdapter(notes)
        val dbHelper = DBHelper(applicationContext)

        addBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, AddNote::class.java)
            startActivity(intent)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_action_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        return super.onCreateOptionsMenu(menu)
    }



}
