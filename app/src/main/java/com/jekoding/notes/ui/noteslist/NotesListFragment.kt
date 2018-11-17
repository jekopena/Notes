package com.jekoding.notes.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val listAdapter = NotesListAdapter { onClickList(it) }

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
        val binding = NotesListFragmentBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLayoutResources()
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
                findNavController().navigate(R.id.action_notesListFragment_to_editNoteFragment)
            }
        })
    }

    private fun initLayoutResources() {
        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = listAdapter

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun onClickList(noteView: NoteView) {
        activity?.application?.toast("click ${noteView.title}")
    }
}
