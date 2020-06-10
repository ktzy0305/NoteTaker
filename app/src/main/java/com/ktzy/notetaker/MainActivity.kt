package com.ktzy.notetaker

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity() {
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var addBtn: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var deleteIcon: Drawable
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000")) // Red

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
        // Delete Icon
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!

        addBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, AddNote::class.java)
            startActivity(intent)
        })

        getAllNotes()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteAdapter.removeItem(viewHolder as NoteViewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                if (dX > 0){
                    swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMargin,
                                         itemView.top + iconMargin,
                                        itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                                      itemView.bottom - iconMargin)
                }
                else{
                    swipeBackground.setBounds((itemView.right + dX).toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                                         itemView.top + iconMargin,
                                        itemView.right - iconMargin,
                                      itemView.bottom - iconMargin)
                }

                swipeBackground.draw(c)
                c.save()
                if (dX > 0){
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                }
                else{
                    c.clipRect((itemView.right + dX).toInt(), itemView.top, itemView.right, itemView.bottom)
                }
                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvNotes)
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
        notesRecyclerView.layoutManager = linearLayoutManager
        noteAdapter = NoteAdapter(notes)
        notesRecyclerView.adapter = noteAdapter
        noteAdapter.notifyDataSetChanged()
    }
}