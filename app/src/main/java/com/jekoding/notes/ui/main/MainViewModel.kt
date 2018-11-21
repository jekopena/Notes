package com.jekoding.notes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jekoding.notes.UserManager
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.framework.Event
import com.jekoding.notes.usecases.SignOut

class MainViewModel(
    private val userManager: UserManager,
    private val resultOkValue: Int,
    private val signoutUC: SignOut
) : ViewModel() {
    val startLoginUI = MutableLiveData<Event<Boolean>>()
    val startAppAsLoggedIn = MutableLiveData<Event<Boolean>>()
    val showLoginRequiredAlert = MutableLiveData<Boolean>()
    val showLoginErrorAlert = MutableLiveData<String>()
    val failure = MutableLiveData<Event<Throwable>>()
    val removeFragments = MutableLiveData<Event<Boolean>>()

    fun onCreate(firstTime: Boolean) {
        if (firstTime) {
            startLoginIfNeeded()
        }
    }

    fun startLoginIfNeeded() {
        closeLoginAlerts()
        userManager.getCurrentUser()?.let {
            startAppAsLoggedIn.value = Event(true)
        } ?: kotlin.run {
            startLoginUI.value = Event(true)
        }
    }

    fun handleLoginResult(resultCode: Int, errorMessage: String?) {
        if (resultCode == resultOkValue) {
            startAppAsLoggedIn.value = Event(true)
        } else {
            errorMessage?.let {
                showLoginErrorAlert.value = it
            } ?: kotlin.run {
                showLoginRequiredAlert.value = true
            }
        }
    }

    fun signout() {
        signoutUC(UseCase.None()) { result ->
            result.onFailure { error ->
                failure.value = Event(error)
            }
            result.onSuccess {
                removeFragments.value = Event(true)
                startLoginIfNeeded()
            }
        }
    }

    private fun closeLoginAlerts() {
        showLoginRequiredAlert.value = false
        showLoginErrorAlert.value = null
    }
}