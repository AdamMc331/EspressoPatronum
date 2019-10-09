package com.adammcneilly.espressopatronum

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * Simple fragment for displaying a user's profile.
 */
class UserProfileFragment : Fragment() {

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getParcelable(ARG_USER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_user_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvFullName.text = user?.fullName
        tvEmailAddress.text = user?.emailAddress
        tvPhoneNumber.text = user?.formattedPhoneNumber
        cbOptedIn.isChecked = user?.optedIn == true
    }

    companion object {
        private const val ARG_USER = "User"

        val FRAGMENT_TAG: String = UserProfileFragment::class.java.simpleName

        fun newInstance(user: User): UserProfileFragment {
            val fragment = UserProfileFragment()

            val args = Bundle()
            args.putParcelable(ARG_USER, user)

            fragment.arguments = args
            return fragment
        }
    }
}