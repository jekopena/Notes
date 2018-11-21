package com.jekoding.notes.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.jekoding.notes.R
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupViewModel()

        viewModel.onCreate(savedInstanceState == null)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.root_container).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.global_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.signout -> {
            viewModel.signout()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            viewModel.handleLoginResult(
                resultCode,
                IdpResponse.fromResultIntent(data)?.error?.message
            )
        }
    }

    private fun setupViewModel() {
        viewModel.startAppAsLoggedIn.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                startAppAsLoggedIn()
            }
        })

        viewModel.startLoginUI.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                startLoginUI()
            }
        })

        viewModel.showLoginRequiredAlert.observe(this, Observer {
            if (it) {
                showLoginAlert(
                    getString(R.string.login_required_message),
                    getString(R.string.login_required_title)
                )
            }
        })

        viewModel.showLoginErrorAlert.observe(this, Observer { message ->
            message?.let { showLoginAlert(it, getString(R.string.login_error_title)) }
        })

        viewModel.failure.observe(this, Observer {
            it.getContentIfNotHandled()?.let { throwable ->
                toast("${throwable.message}")
            }
        })

        viewModel.removeFragments.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                removeFragments()
            }
        })
    }

    private fun startLoginUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun startAppAsLoggedIn() {
        val finalHost = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()
    }

    private fun removeFragments() {
        supportFragmentManager.findFragmentById(R.id.root_container)?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    private fun showLoginAlert(message: String, title: String) {
        alert(message, title) {
            yesButton { viewModel.startLoginIfNeeded() }
        }.build().apply {
            setCanceledOnTouchOutside(false)
        }.show()
    }
}
