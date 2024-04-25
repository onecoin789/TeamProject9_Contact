package com.example.teamproject9_contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teamproject9_contact.fragment.ContactDetailFragment
import com.example.teamproject9_contact.fragment.ContactListFragment

class MainViewPagerAdapter(fm: FragmentActivity, private val index: String?) :
    FragmentStateAdapter(fm) {
    private var fragments = mutableListOf<Fragment>(
        ContactListFragment(),
        MyPageFragment()
    )

    fun setFragment(fragment: Fragment, position: Int){
        fragments[position] = fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { ContactListFragment.newInstance(index) }
            1 -> { MyPageFragment() }
            else -> {
                throw IllegalArgumentException("Fragment does not exist")
            }
        }
    }
}
