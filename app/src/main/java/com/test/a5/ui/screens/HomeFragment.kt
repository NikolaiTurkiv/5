package com.test.a5.ui.screens

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.a5.R
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.databinding.FragmentHomeBinding
import com.test.a5.ui.screens.adapters.BetsAdapter
import com.test.a5.ui.screens.adapters.SpinAdapter
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()

    private var listBankroll: List<BankrollEntity>? = null
    private var bankrollName: String = ""

    private val adapter by lazy {
        BetsAdapter(LayoutInflater.from(requireContext()), {
            viewModel.updateBet(
                it.copy(
                    isSuccess = true,
                    isFailure = false,
                    isWait = false,
                    actualCapital = ((it.odd * it.amount) - it.amount)
                ),
                bankrollName
            )
        }, {
            viewModel.updateBet(
                it.copy(
                    isSuccess = false,
                    isFailure = true,
                    isWait = false,
                    actualCapital = -it.amount
                ),
                bankrollName
            )

        }, {
            viewModel.updateBet(
                it.copy(isSuccess = false, isFailure = false, isWait = true),
                bankrollName
            )

        }, {
            viewModel.removeBet(it.id, bankrollName)
        })
    }

    private val spinnerAdapter by lazy {
        SpinAdapter()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission( android.Manifest.permission.POST_NOTIFICATIONS)
        }
        viewModel.getBankrolls()
        viewModel.getBets()
        initAdapter()
        initObserver()
        initView()
        initSwipeLayout()

    }

    private fun requestPermission(permiss: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                permiss
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permiss
            ) -> {
                requestPermissionLauncher.launch(
                    permiss
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    permiss
                )
            }
        }
    }

    private fun initSwipeLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNamedBets(bankrollName)
        }
    }

    private fun initObserver() {
        viewModel.isRefreshed.observe(viewLifecycleOwner) {
             binding.swipeRefresh.isRefreshing = false
        }

        viewModel.betsLd.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                adapter.updateList(it)
            } else {
                adapter.updateList(listOf())
            }
        }

        viewModel.bankrollLIstLd.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                spinnerAdapter.updateList(it)
            }
            listBankroll = it
        }

        viewModel.betVisibility.observe(viewLifecycleOwner) { betIsVisible ->
            viewModel.bankrollsVisibility.observe(viewLifecycleOwner) { bankrollIsVisible ->
                if (betIsVisible && bankrollIsVisible) {
                    binding.tvHaventBankroll.visibility = View.GONE
                    binding.swipeRefresh.visibility = View.VISIBLE
                    binding.spinnerMain.visibility = View.VISIBLE
                }

                if (!betIsVisible && bankrollIsVisible) {
                    binding.tvHaventBankroll.visibility = View.VISIBLE
                    binding.tvHaventBankroll.text = "Create new bet"
                    binding.swipeRefresh.visibility = View.GONE
                    binding.spinnerMain.visibility = View.GONE
                }

                if (!betIsVisible && !bankrollIsVisible) {
                    binding.tvHaventBankroll.visibility = View.VISIBLE
                    binding.swipeRefresh.visibility = View.GONE
                    binding.spinnerMain.visibility = View.GONE
                }
            }
        }
    }

    private fun initView() {
        binding.btnCreateBancroll.setOnClickListener {
            BetDialogFragment().show(childFragmentManager, BetDialogFragment.BET_DIALOG_TAG)
        }

    }

    private fun initAdapter() {
        binding.rv.adapter = adapter
        binding.spinnerMain.adapter = spinnerAdapter

        binding.spinnerMain.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bankrollName = listBankroll?.get(p2)?.bankrollName ?: ""
                 viewModel.getNamedBets(bankrollName)
                viewModel.getBankroll(bankrollName)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            Log.d("TAAG", tag.toString())
        }

        const val TAG = "HOME_F"

    }

}