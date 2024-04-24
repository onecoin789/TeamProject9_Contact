package com.example.teamproject9_contact.data

import com.example.teamproject9_contact.Contact
import com.example.teamproject9_contact.R

object ContactList {
    val list = mutableListOf<Contact>(
        Contact("asd", "asd", "asd", R.drawable.image1, true),
        Contact("asd2", "asd3", "asd2", R.drawable.heart2, false),
        Contact("asd3", "asd4", "asd3", R.drawable.ic_contactfrag_star_fill, true),
        Contact("asd4", "asd2", "asd4", R.drawable.image1, true)
    )
}