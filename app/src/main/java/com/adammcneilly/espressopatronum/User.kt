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
        var phoneNumber: String = "") : Parcelable {

    val fullName: String
        get() = "$firstName $lastName"

    // https://stackoverflow.com/a/5115433/3131147
    val formattedPhoneNumber: String
        get() = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3")
}