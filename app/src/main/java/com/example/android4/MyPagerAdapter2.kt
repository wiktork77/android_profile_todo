package com.example.android4

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter2(f: Fragment): FragmentStateAdapter(f) {
    private val PAGE_COUNT = 4
    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(position)
    }

}