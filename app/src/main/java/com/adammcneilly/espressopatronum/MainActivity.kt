package com.adammcneilly.espressopatronum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

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
