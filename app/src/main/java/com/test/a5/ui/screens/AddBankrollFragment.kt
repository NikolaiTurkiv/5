package com.test.a5.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.test.a5.R
import com.test.a5.databinding.FragmentAddBankrollBinding
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBankrollFragment : Fragment(R.layout.fragment_add_bankroll) {

    private val binding by viewBinding<FragmentAddBankrollBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners(){
        binding.btnSave.setOnClickListener {
            val name = binding.etBankrollName.text.toString()
            val capital = binding.etInitialCapital.text.toString()

            if(name.isNotEmpty() && capital.isNotEmpty()){
                viewModel.saveBankroll(name,capital.toDouble())
                Toast.makeText(requireContext(),"SUCCESS",Toast.LENGTH_SHORT).show()
                binding.etBankrollName.setText("")
                binding.etInitialCapital.setText("")
                viewModel.getBets()
                viewModel.getBankrolls()
            }else{
                binding.etBankrollName.setText("")
                binding.etInitialCapital.setText("")
                Toast.makeText(requireContext(),"sda",Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
         @JvmStatic
        fun newInstance() =
            AddBankrollFragment()
    }
}