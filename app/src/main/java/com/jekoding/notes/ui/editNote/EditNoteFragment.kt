package com.jekoding.notes.ui.editNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jekoding.notes.databinding.EditNoteFragmentBinding
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditNoteFragment : Fragment() {

    private val viewModel: EditNoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EditNoteFragmentBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        return binding.root
    }

     private fun setupViewModel() {
        viewModel.failure.observe(this, Observer {
            it.getContentIfNotHandled()?.let { throwable ->
                activity?.application?.toast("${throwable.message}")
            }

        })

        viewModel.navigateBack.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        })
    }
}
