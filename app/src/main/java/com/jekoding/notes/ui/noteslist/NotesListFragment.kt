package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jekoding.notes.R
import com.jekoding.notes.model.NoteView
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

        setViewModelObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLayoutResources()

        viewModel.loadNotes()
    }

    private fun setViewModelObservers() {
        viewModel.notes.observe(this, Observer<List<NoteView>> {
            listAdapter.setNotes(it)
            listAdapter.notifyDataSetChanged()
        })

        viewModel.failure.observe(this, Observer {
            Toast.makeText(activity, "ERROR: $it", Toast.LENGTH_LONG).show()
        })
    }

    private fun initLayoutResources() {
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter
    }
}
