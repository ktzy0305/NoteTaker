package com.ktzy.notetaker

import android.app.SearchManager
import android.content.Context
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
    private var addBtn: FloatingActionButton? = null
    private var notesRecyclerView: RecyclerView? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    private val notes = listOf(
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
        notesRecyclerView?.layoutManager = linearLayoutManager
        notesRecyclerView?.adapter = NoteAdapter(notes)

        addBtn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, AddNote::class.java)
            startActivity(intent)
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_actionbar_menu, menu)
//        // Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu!!.findItem(R.id.search).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//
//        return true
//    }

}
