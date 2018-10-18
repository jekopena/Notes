package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jekoding.notes.R
import kotlinx.android.synthetic.main.main_fragment.*

class NotesListFragment : Fragment() {

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private lateinit var viewModel: NotesListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotesListViewModel::class.java)

        rvNotesList.setHasFixedSize(true)
        rvNotesList.layoutManager = LinearLayoutManager(activity)
        rvNotesList.adapter = NotesListAdapter(viewModel.notes) {

        }
    }


}
