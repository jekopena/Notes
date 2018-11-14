package com.jekoding.notes.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.jekoding.notes.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        startLoginIfNeeded()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun startLoginIfNeeded() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
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
        } else {
            startFragmentNavigation()
        }
    }

    private fun startFragmentNavigation() {
        val finalHost = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                startFragmentNavigation()
            } else {
                if (response == null) {
                    showLoginAlert(
                        getString(R.string.login_required_message),
                        getString(R.string.login_required_title)
                    )
                } else {
                    showLoginAlert(
                        "${response.error?.message}",
                        getString(R.string.login_error_title)
                    )
                }
            }
        }
    }

    private fun showLoginAlert(message: String, title: String) {
        alert(message, title) {
            yesButton { startLoginIfNeeded() }
        }.build().apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }.show()
    }
}
