package com.example.cobratelo_app.ui.pay_history.rent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.databinding.CanceledPayItemBinding

class CanceledRentPayAdapter: ListAdapter<CanceledRentPayUI, CanceledRentPayAdapter.CanceledRentPayViewHolder>(DiffUtils) {

    inner class CanceledRentPayViewHolder(
        private val binding: CanceledPayItemBinding
    ): ViewHolder(binding.root) {
        fun bind(item: CanceledRentPayUI) {
            binding.state = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanceledRentPayViewHolder {
        return CanceledRentPayViewHolder(CanceledPayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CanceledRentPayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object DiffUtils: DiffUtil.ItemCallback<CanceledRentPayUI>(){
        override fun areItemsTheSame(
            oldItem: CanceledRentPayUI,
            newItem: CanceledRentPayUI
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: CanceledRentPayUI,
            newItem: CanceledRentPayUI
        ): Boolean {
            return oldItem.date == newItem.date
        }

    }
}