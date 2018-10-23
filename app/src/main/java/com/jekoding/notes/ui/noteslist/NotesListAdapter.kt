package com.jekoding.notes.ui.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jekoding.notes.R
import com.jekoding.notes.model.NoteView
import kotlinx.android.synthetic.main.note_row.view.*

class NotesListAdapter(private val listener: (NoteView) -> Unit) :
        RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    private val notes = arrayListOf<NoteView>()

    class NotesListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: NoteView, listener: (NoteView) -> Unit) = with(itemView) {
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

    fun setNotes(newNotes: List<NoteView>?) {
        this.notes.clear()
        if (newNotes != null) {
            this.notes.addAll(newNotes)
        }
    }
}