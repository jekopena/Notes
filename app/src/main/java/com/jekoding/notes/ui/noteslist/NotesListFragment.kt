package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jekoding.notes.Note
import com.jekoding.notes.R
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModel()
    private val listAdapter =  NotesListAdapter {
        Toast.makeText(activity, "click ${it.title}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = NotesListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.notes.observe(this, Observer<List<Note>> { notes ->
            listAdapter.setNotes(notes)
            listAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter

        viewModel.loadNotes()
    }

}
