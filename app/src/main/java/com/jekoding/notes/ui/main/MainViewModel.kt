package com.jekoding.notes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jekoding.notes.framework.Event
import com.jekoding.notes.framework.NotesUserManager

class MainViewModel(private val notesUserManager: NotesUserManager,
                    private val resultOkValue: Int) : ViewModel() {
    val startLogin = MutableLiveData<Event<Boolean>>()
    val startAppAsLoggedIn = MutableLiveData<Event<Boolean>>()
    val showLoginRequiredAlert = MutableLiveData<Boolean>()
    val showLoginErrorAlert = MutableLiveData<String>()

    fun onCreate(firstTime: Boolean) {
        if (firstTime) {
            startLoginIfNeeded()
        }
    }

    fun startLoginIfNeeded() {
        closeLoginAlerts()
        val user = notesUserManager.getCurrentUser()
        if (user == null) {
            startLogin.value = Event(true)
        } else {
            startAppAsLoggedIn.value = Event(true)
        }
    }

    fun handleLoginResult(resultCode: Int, errorMessage: String?) {
        if (resultCode == resultOkValue) {
            startAppAsLoggedIn.value = Event(true)
        } else {
            if (errorMessage == null) {
                showLoginRequiredAlert.value = true
            } else {
                showLoginErrorAlert.value = errorMessage
            }
        }
    }

    private fun closeLoginAlerts() {
        showLoginRequiredAlert.value = false
        showLoginErrorAlert.value = null
    }
}