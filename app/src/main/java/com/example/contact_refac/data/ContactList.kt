package com.example.contact_refac.data

import com.example.contact_refac.R

object ContactList{
    val list = mutableListOf<Contact>(
        Contact("김채원", "010-1111-1111", "asdn123@naver.com", R.drawable.contact_image1.toString(), false, false),
        Contact("송강", "010-2222-2222", "asjnjnsa54@naver.com", R.drawable.contact_image2.toString(), false, false),
        Contact("김민정", "010-3333-3333", "nefjk56@naver.com", R.drawable.contact_image3.toString(), false, false),
        Contact("유지민", "010-4444-4444", "akfmkm855@naver.com", R.drawable.contact_image4.toString(), false, false),
        Contact("김태형", "010-5555-5555", "kpokkg445@naver.com", R.drawable.contact_image5.toString(), false, false),
        Contact("김석진", "010-6666-6666", "asllkkg45@naver.com", R.drawable.contact_image6.toString(), false, false)
    )

    var myContact = Contact(
        "홍길동",
        "010-0000-0000",
        "aaaaaaa11@naver.com",
        R.drawable.image1.toString(),
        false,
        false
    )
}