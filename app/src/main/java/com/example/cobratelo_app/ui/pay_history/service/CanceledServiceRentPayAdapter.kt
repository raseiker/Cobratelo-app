package com.example.cobratelo_app.ui.pay_history.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.databinding.CanceledServicePayItemBinding

class CanceledServiceRentPayAdapter(

): ListAdapter<CanceledServicePayUI, CanceledServiceRentPayAdapter.CanceledServiceRentPayViewHolder>(DiffCallback) {
    inner class CanceledServiceRentPayViewHolder(
        private val binding: CanceledServicePayItemBinding
    ): ViewHolder(binding.root) {
        fun bind(currentItem: CanceledServicePayUI?) {
            binding.state = currentItem
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CanceledServiceRentPayViewHolder {
        return CanceledServiceRentPayViewHolder(CanceledServicePayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CanceledServiceRentPayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CanceledServicePayUI>(){
        override fun areItemsTheSame(
            oldItem: CanceledServicePayUI,
            newItem: CanceledServicePayUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CanceledServicePayUI,
            newItem: CanceledServicePayUI
        ): Boolean {
            return oldItem.date == newItem.date
                    && oldItem.energyAmount == newItem.energyAmount
                    && oldItem.waterAmount == newItem.waterAmount
        }

    }
}