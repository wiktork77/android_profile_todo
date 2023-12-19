package com.example.android4

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.io.Serializable

class MyPagerAdapter2(f: Fragment, val l: MutableList<PhotoItem>, var pos: Int = 0): FragmentStateAdapter(f) {
    override fun getItemCount(): Int {
        return l.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(position, l as Serializable)
    }



}