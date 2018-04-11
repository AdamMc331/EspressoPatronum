package com.adammcneilly.espressopatronum

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Simple POJO class
 */
@Parcelize
data class User(
        var firstName: String = "",
        var lastName: String = "",
        var emailAddress: String = "",
        var phoneNumber: String = "",
        var optedIn: Boolean = false) : Parcelable {

    val fullName: String
        get() = "$firstName $lastName"

    // https://stackoverflow.com/a/5115433/3131147
    val formattedPhoneNumber: String
        get() = "(${phoneNumber.substring(0, 3)})-${phoneNumber.substring(3, 6)}-${phoneNumber.substring(6)}"
}