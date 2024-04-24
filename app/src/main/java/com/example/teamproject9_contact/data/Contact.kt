package com.example.teamproject9_contact

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact (
    val name: String,
    val phoneNum: String,
    val email: String,
    val imgResource: Int,
    val bookmark: Boolean
) : Parcelable