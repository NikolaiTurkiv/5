package com.test.a5.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.test.a5.R
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.databinding.FragmentOptionsBinding
import com.test.a5.ui.screens.adapters.SpinAdapter
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : Fragment(R.layout.fragment_options) {


    private val binding by viewBinding<FragmentOptionsBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()
    private val adapter by lazy {
        SpinAdapter()
    }
    private var listBankroll: List<BankrollEntity>? = null
    private var bankrollName: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBankrolls()
        initAdapter()
        initObservers()
        initClickListener()
    }

    private fun initObservers() {
        viewModel.bankrollLIstLd.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            listBankroll = it
        }
        viewModel.isBankrollRemoved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        binding.spinnerOption.adapter = adapter
        binding.spinnerOption.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    bankrollName = listBankroll?.get(p2)?.bankrollName ?: ""
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    viewModel.getBankrolls()
                }
            }
    }

    private fun initClickListener() {
        binding.button.setOnClickListener {
            if(bankrollName.isNotEmpty() && bankrollName != "Choose bankroll"){
                viewModel.removeBankroll(bankrollName)
                viewModel.removeBetByName(bankrollName)
                binding.spinnerOption.adapter = null
                binding.spinnerOption.adapter = adapter
            }else{
                Toast.makeText(requireContext(),"Please, choose a bet",Toast.LENGTH_SHORT).show()
            }

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = OptionsFragment()
    }
}