package com.test.a5.ui.screens.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.databinding.SpinnerItemBinding

class SpinAdapter() : BaseAdapter() {

    private var items = mutableListOf<BankrollEntity>()

    fun updateList(newList: List<BankrollEntity>) {
        items.clear()
        items.addAll(newList)

        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size


    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val binding = p1?.tag as SpinnerItemBinding? ?: p2?.context?.let { createBinding(it) }

        binding?.tvItemName?.text = items[p0].bankrollName

        return binding!!.root
    }

    private fun createBinding(context: Context): SpinnerItemBinding {
        val binding = SpinnerItemBinding.inflate(LayoutInflater.from(context))
        binding.root.tag = binding
        return binding
    }

}