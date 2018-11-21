package com.jekoding.notes.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jekoding.notes.R
import com.jekoding.notes.databinding.NotesListFragmentBinding
import com.jekoding.notes.models.NoteView
import kotlinx.android.synthetic.main.notes_list_fragment.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModel()
    private val listAdapter = NotesListAdapter { navigateToEditNote(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NotesListFragmentBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLayoutResources()

        if (savedInstanceState == null) {
            viewModel.loadNotes()
        }
    }

    private fun setupViewModel() {
        viewModel.failure.observe(this, Observer {
            it.getContentIfNotHandled()?.let { throwable ->
                activity?.application?.toast("${throwable.message}")
            }
        })

        viewModel.noteViews.observe(this, Observer<List<NoteView>> {
            listAdapter.submitList(it)
        })

        viewModel.navigateToAddNote.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                navigateToEditNote(null)
            }
        })

        viewModel.progressBarVisibility.observe(this, Observer {
            swipeContainer.isRefreshing = it
        })
    }

    private fun initLayoutResources() {
        initRecyclerView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initRecyclerView() {
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter

        swipeContainer.setOnRefreshListener {
            viewModel.loadNotes()
        }
        swipeContainer.setColorSchemeResources(R.color.colorAccent)
    }

    private fun navigateToEditNote(noteView: NoteView?) {
        val action = NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment()
        action.setNoteViewId(noteView?.id?.toInt() ?: 0)
        findNavController().navigate(action)
    }
}
