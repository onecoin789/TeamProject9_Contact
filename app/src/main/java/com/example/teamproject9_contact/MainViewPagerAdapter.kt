package com.example.teamproject9_contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teamproject9_contact.fragment.ContactDetailFragment
import com.example.teamproject9_contact.fragment.ContactListFragment

class MainViewPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    private var fragments = mutableListOf<Fragment>(
        ContactListFragment(),
        ContactDetailFragment()
    )

    fun setFragment(fragment: Fragment, position: Int){
        fragments[position] = fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
