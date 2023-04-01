package com.test.a5.ui.screens.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import com.test.a5.databinding.BetItemBinding
import com.test.a5.databinding.SpinnerItemBinding

class BetsAdapter(
    private val inflater: LayoutInflater,
    private val itemClickAccept: (item: BetEntity) -> Unit,
    private val itemClickDecline: (item: BetEntity) -> Unit,
    private val itemClickWait: (item: BetEntity) -> Unit,
    private val itemClickDelete: (item: BetEntity) -> Unit,
) : RecyclerView.Adapter<BetsAdapter.BetsViewHolder>() {

    private var items = mutableListOf<BetEntity>()

    fun updateList(newList: List<BetEntity>) {
        items.clear()
        items.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetsViewHolder {
        return BetsViewHolder(BetItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BetsViewHolder, position: Int) {
        holder.bind(items[position], itemClickAccept,itemClickDecline,itemClickWait,itemClickDelete)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class BetsViewHolder(private val binding: BetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: BetEntity, itemClickAccept: (item: BetEntity) -> Unit,
            itemClickDecline: (item: BetEntity) -> Unit,
            itemClickWait: (item: BetEntity) -> Unit,
            itemClickDelete: (item: BetEntity) -> Unit
        ) {
            binding.tvBetName.text = item.name
            binding.tvBet.text = item.amount.toString()
            binding.tvOdd.text = item.odd.toString()

            binding.imDelete.setOnClickListener {
                itemClickDelete.invoke(item)
            }
            binding.imSuccess.setOnClickListener {
                itemClickAccept.invoke(item)
            }
            binding.imWait.setOnClickListener {
                itemClickWait.invoke(item)
            }
            binding.imFail.setOnClickListener {
                itemClickDecline.invoke(item)
            }

            if(item.isSuccess){
                binding.tvProfit.setTextColor(Color.parseColor("#0C9F0E"))
                binding.tvProfit.text = ((item.odd * item.amount) - item.amount).toString()
            }

            if (item.isFailure){
                binding.tvProfit.setTextColor(Color.parseColor("#E91E63"))
                binding.tvProfit.text = (-item.amount).toString()
            }

            if(item.isWait){
                binding.tvProfit.setTextColor(Color.parseColor("#000000"))
                binding.tvProfit.text = ""
            }

        }
    }
}