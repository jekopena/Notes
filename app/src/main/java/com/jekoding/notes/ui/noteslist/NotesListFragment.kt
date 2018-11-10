package com.jekoding.notes.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jekoding.notes.R
import com.jekoding.notes.models.NoteView
import kotlinx.android.synthetic.main.notes_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModel()
    private val listAdapter = NotesListAdapter {
        Toast.makeText(activity!!.applicationContext, "click ${it.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

        if (savedInstanceState == null) {
            viewModel.loadNotes()
        }
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

    private fun setupViewModel() {
        viewModel.failure.observe(this, Observer {
            it.getContentIfNotHandled()?.let { throwable ->
                Toast.makeText(
                    activity!!.applicationContext,
                    "${throwable.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.noteViews.observe(this, Observer<List<NoteView>> {
            listAdapter.setNotes(it)
            listAdapter.notifyDataSetChanged()
        })

        viewModel.navigateToAddNote.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_notesListFragment_to_editNoteFragment)
            }
        })
    }

    private fun initLayoutResources() {
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter

        btAddNote.setOnClickListener {
            viewModel.addNoteClick()
        }
    }

}
