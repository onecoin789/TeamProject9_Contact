package com.example.teamproject9_contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fm: FragmentActivity):
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
//            0 ->{  }
            1 ->{ MyPageFragment() }
            else -> {
                return BlankFragment()
//                throw IllegalArgumentException("Fragment does not exist")
            }
        }
    }
}