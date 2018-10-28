package com.jekoding.notes.ui.noteslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jekoding.notes.R
import com.jekoding.notes.models.NoteView
import kotlinx.android.synthetic.main.notes_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesListFragment : Fragment() {

    private val notesListViewModel: NotesListViewModel by viewModel()
    private val listAdapter = NotesListAdapter {
        Toast.makeText(activity, "click ${it.title}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = NotesListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModelObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLayoutResources()
    }

    private fun setViewModelObservers() {
        notesListViewModel.noteViews.observe(this, Observer<List<NoteView>> {
            listAdapter.setNotes(it)
            listAdapter.notifyDataSetChanged()
        })
    }

    private fun initLayoutResources() {
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter

        btAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_notesListFragment_to_editNoteFragment)
        }
    }
}
