package com.example.teamproject9_contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    private val fragments = listOf<Fragment>(
        ContactListFragment(),
        ContactDetailFragment()
    )

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
