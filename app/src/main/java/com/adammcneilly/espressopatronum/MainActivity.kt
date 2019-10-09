package com.adammcneilly.espressopatronum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), OnUserRegisteredListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(RegistrationFragment.newInstance(), RegistrationFragment.FRAGMENT_TAG)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
    }

    override fun onUserRegistered(user: User) {
        replaceFragment(UserProfileFragment.newInstance(user), UserProfileFragment.FRAGMENT_TAG)
    }
}
