package com.test.a5.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.test.a5.R
import com.test.a5.databinding.FragmentMainBinding
import com.test.a5.ui.screens.adapters.PagerAdapter
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding<FragmentMainBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductDetailsViewPager()
    }

    private fun initProductDetailsViewPager() {
        val adapter = PagerAdapter(requireActivity())
        binding.vpMain.adapter = adapter
        TabLayoutMediator(binding.tlMain, binding.vpMain) { tab, pos ->
            when (pos) {
                0 -> tab.setIcon(R.drawable.ic_home_24)
                1 -> tab.setIcon(R.drawable.ic_stats)
               else -> tab.setIcon(R.drawable.ic_setting)
            }
        }.attach()
     }

    companion object {
        @JvmStatic
        fun newInstance( ) =
            MainFragment()
    }
}
