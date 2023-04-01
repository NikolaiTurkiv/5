package com.test.a5.ui.screens.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.a5.ui.screens.AddBankrollFragment
import com.test.a5.ui.screens.AddBetFragment

class AddBetAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity)  {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> AddBetFragment.newInstance()
           else-> AddBankrollFragment.newInstance()
       }
    }
}