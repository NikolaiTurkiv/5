package com.test.a5.ui.screens.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.a5.ui.screens.HomeFragment
import com.test.a5.ui.screens.OptionsFragment
import com.test.a5.ui.screens.StatisticFragment

class PagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1-> StatisticFragment()
            else -> OptionsFragment()
        }
    }

}