package com.example.contact_refac.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contact_refac.data.ContactList
import com.example.contact_refac.presentation.main.fragment.contactList.ContactListFragment
import com.example.contact_refac.presentation.main.fragment.detailPage.DetailPageFragment

class MainViewPagerAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {
    val fragments = mutableListOf<Fragment>(
        ContactListFragment(),
        DetailPageFragment.newInstance(ContactList.myContact)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}