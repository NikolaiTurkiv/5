package com.test.a5.ui.screens

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.test.a5.R
import com.test.a5.databinding.FragmentBetDialogBinding
import com.test.a5.ui.screens.adapters.AddBetAdapter
import com.test.a5.ui.screens.adapters.PagerAdapter
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BetDialogFragment : DialogFragment(R.layout.fragment_bet_dialog) {

    private val binding by viewBinding<FragmentBetDialogBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductDetailsViewPager()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun initProductDetailsViewPager() {
        val adapter = AddBetAdapter(requireActivity())
        binding.vpBets.adapter = adapter
        TabLayoutMediator(binding.tablBets, binding.vpBets) { tab, pos ->
            when (pos) {
                0 -> tab.text = "BET"
                else -> tab.text = "BANKROLL"
            }
        }.attach()

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BetDialogFragment()

        const val BET_DIALOG_TAG = "BET_DIALOG_TAG"
    }
}