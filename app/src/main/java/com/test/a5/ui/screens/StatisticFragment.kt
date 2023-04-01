package com.test.a5.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.test.a5.R
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import com.test.a5.databinding.FragmentStatisticBinding
import com.test.a5.ui.screens.adapters.SpinAdapter
import com.test.a5.ui.viewmodels.BetBankrollViewModel
import com.test.a5.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val binding by viewBinding<FragmentStatisticBinding>()
    private val viewModel by viewModels<BetBankrollViewModel>()
    private val adapter by lazy {
        SpinAdapter()
    }
    private var listBankroll: List<BankrollEntity>? = null
    private var bankrollName: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBets()
        viewModel.getBankrolls()
        initAdapter()
        initObservers()

    }


    private fun initAdapter() {
        binding.spinnerStatistic.adapter = adapter
        binding.spinnerStatistic.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    bankrollName = listBankroll?.get(p2)?.bankrollName ?: ""
                    viewModel.getNamedBets(bankrollName)
                    if (bankrollName.isNotEmpty())
                        viewModel.getBankroll(bankrollName)
                    Log.d("BANKROLLNAME33", bankrollName)

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun initObservers() {
        viewModel.bankrollLIstLd.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            listBankroll = it
        }
        viewModel.betsLd.observe(viewLifecycleOwner) { bets ->
            if (!bets.isNullOrEmpty())
                initGraph(bets)
            else
                initGraph(listOf())

            val sumSuccess = bets.filter { it.isSuccess }.sumOf { it.actualCapital }
            val sumFail = bets.filter { it.isFailure }.sumOf { it.actualCapital }

            val successList = bets.filter { it.isSuccess }
            val failList = bets.filter { it.isFailure }

            val sumActCap = bets.filter { it.isSuccess || it.isFailure }.sumOf { it.actualCapital }
            val sumAmount = bets.filter { it.isSuccess || it.isFailure }.sumOf { it.amount }

            val averageBet = bets.map { it.amount }.toTypedArray().average()
            val averageOdd = bets.map { it.odd }.toTypedArray().average()
            val successBetPercent =
                if (successList.isNotEmpty()) (successList.size / bets.size) * 100 else 0

            viewModel.bankrollLd.observe(viewLifecycleOwner) {
                val actualCapital = it.capitalStart + sumSuccess - sumFail
                val benefits = actualCapital - it.capitalStart
                val numberOfBets = bets.size
                val roi = ((sumActCap - sumAmount) / sumAmount) * 100
                val roc = actualCapital / it.capitalStart * 100
                if (bankrollName != "Choose bankroll") {
                    with(binding) {
                        tvBalance.text = actualCapital.toInt().toString()
                        tvAverageBet.text = averageBet.toInt().toString()
                        tvBenefits.text = benefits.toInt().toString()
                        tvRoc.text = roc.toInt().toString()
                        tvRoi.text = roi.toInt().toString()
                        tvAverageOdd.text = averageOdd.toInt().toString()
                        tvNumberOfBet.text = numberOfBets.toInt().toString()
                        tvSuccess.text = successBetPercent.toInt().toString()
                    }
                } else {
                    with(binding) {
                        tvBalance.text = ""
                        tvAverageBet.text = ""
                        tvBenefits.text = ""
                        tvRoc.text = ""
                        tvRoi.text = ""
                        tvAverageOdd.text = ""
                        tvNumberOfBet.text = ""
                        tvSuccess.text = ""
                    }
                }

            }
        }
    }


    private fun initGraph(list: List<BetEntity>) {

        binding.graphView.removeAllSeries()

        if (list.isEmpty()) {
            val series = LineGraphSeries(
                arrayOf(DataPoint(0.0, 0.0))
            )
            seriesOption(series)
            binding.graphView.addSeries(series)

        } else {
            val listDataPoint = list.filter { entity ->
                entity.isSuccess
            }.mapIndexed { index, betEntity ->
                DataPoint(index.toDouble(), ((betEntity.odd * betEntity.amount) - betEntity.amount))
            }.toTypedArray()

            val series = LineGraphSeries(
                listDataPoint
            )
            seriesOption(series)
            binding.graphView.addSeries(series)
        }
    }


    private fun seriesOption(series: LineGraphSeries<DataPoint>) {
        series.color = Color.GREEN
        series.isDrawDataPoints = true
        series.dataPointsRadius = 10F
        series.thickness = 8
        binding.graphView.gridLabelRenderer.gridColor = Color.GREEN
        binding.graphView.gridLabelRenderer.numVerticalLabels = 5
        binding.graphView.gridLabelRenderer.verticalLabelsColor = Color.GREEN
    }


    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}