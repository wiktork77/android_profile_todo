package com.example.android4

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter2(f: Fragment): FragmentStateAdapter(f) {
    private val PAGE_COUNT = 2
    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TodosFragment.newInstance("", "")
            1 -> return MainFragment.newInstance("", "")
        }
        return MainFragment.newInstance("", "")
    }
}