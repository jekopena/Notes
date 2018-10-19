package com.jekoding.notes.ui.noteslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jekoding.notes.R
import com.jekoding.notes.Note
import kotlinx.android.synthetic.main.note_row.view.*

class NotesListAdapter(val notes: List<Note>, private val listener: (Note) -> Unit) :
        RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    class NotesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Note, listener: (Note) -> Unit) = with(itemView) {
            tvTitle.text = item.title
            setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_row, parent, false)
        return NotesListViewHolder(view)
    }


    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        return holder.bind(notes[position], listener)
    }

    override fun getItemCount() = notes.size
}