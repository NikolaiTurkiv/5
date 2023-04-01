package com.test.a5.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.a5.R
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.databinding.FragmentAddBetBinding
import com.test.a5.ui.screens.adapters.SpinAdapter
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddBetFragment : Fragment(R.layout.fragment_add_bet) {

    private val binding by viewBinding<FragmentAddBetBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()

    private val adapter by lazy { SpinAdapter() }
    private var listBankroll: List<BankrollEntity>? = null
    private var bankrollName: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBankrolls()
        initAdapter()
        initObserver()
        initClickListeners()
    }


    private fun initAdapter() {
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    bankrollName = listBankroll?.get(p2)?.bankrollName ?: ""
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun initClickListeners() {
        binding.btnSaveBet.setOnClickListener {
            val betName = binding.etNameOfBet.text.toString()
            val odd = binding.etOdd.text.toString()
            val amount = binding.etAmount.text.toString()
            val bankroll = bankrollName

            if (betName.isNotEmpty() && odd.isNotEmpty() && amount.isNotEmpty() && bankroll.isNotEmpty() && bankroll != "Choose bankroll") {
                viewModel.saveBet(
                    name = betName,
                    odd = odd.toDouble(),
                    amount = amount.toDouble(),
                    bankrollName = bankroll
                )

                binding.etNameOfBet.setText("")
                binding.etOdd.setText("")
                binding.etAmount.setText("")

            } else {
                Toast.makeText(requireContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun initObserver() {
        viewModel.bankrollLIstLd.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            listBankroll = it

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddBetFragment()

        const val TAG = "ADD_BET_TAG"
    }
}