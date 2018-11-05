package com.jekoding.notes.ui.noteslist

import androidx.recyclerview.widget.DiffUtil
import com.jekoding.notes.R
import com.jekoding.notes.framework.DataBindingAdapter
import com.jekoding.notes.models.NoteView

class NotesListAdapter(listener: (NoteView) -> Unit) :
    DataBindingAdapter<NoteView>(listener, DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<NoteView>() {
        override fun areItemsTheSame(oldItem: NoteView, newItem: NoteView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteView, newItem: NoteView): Boolean {
            return oldItem.title == newItem.title && oldItem.text == newItem.text
        }
    }

    override fun getItemViewType(position: Int) = R.layout.note_row
}