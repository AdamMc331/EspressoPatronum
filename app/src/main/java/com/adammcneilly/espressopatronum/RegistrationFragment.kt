package com.adammcneilly.espressopatronum

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_registration.*

/**
 * Fragment containing a form for registration.
 */
class RegistrationFragment : Fragment(), View.OnClickListener {

    private val callback: OnUserRegisteredListener by lazy { activity as OnUserRegisteredListener }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_registration, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.registerButton -> {
                val isValid = validateInput()

                if (isValid) {
                    val user = User(
                            etFirstName.text.toString(),
                            etLastName.text.toString(),
                            etEmail.text.toString(),
                            etPhone.text.toString()
                    )

                    callback.onUserRegistered(user)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (etFirstName.text.toString().isEmpty()) {
            etFirstName.error = getString(R.string.err_empty_first_name)
            isValid = false
        }

        if (etLastName.text.toString().isEmpty()) {
            etLastName.error = getString(R.string.err_empty_last_name)
            isValid = false
        }

        if (etEmail.text.toString().isEmpty()) {
            etEmail.error = getString(R.string.err_empty_email_address)
            isValid = false
        } else if (!etEmail.text.toString().matches(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"))) {
            etEmail.error = getString(R.string.err_invalid_email)
            isValid = false
        }

        if (etPhone.text.toString().isEmpty()) {
            etPhone.error = getString(R.string.err_empty_phone_number)
            isValid = false
        } else if (etPhone.text.toString().length != 10) {
            etPhone.error = getString(R.string.err_invalid_phone_number)
            isValid = false
        }

        return isValid
    }

    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()

        val FRAGMENT_TAG: String = RegistrationFragment::class.java.simpleName
    }
}