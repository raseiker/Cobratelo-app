package com.example.cobratelo_app.ui.pay_history.rent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.databinding.PendingPayItemBinding

class PendingRentPayAdapter(
    val listener: PendingRentPayListener,
): ListAdapter<PendingRentPayUI, PendingRentPayAdapter.PendingRentPayViewHolder>(DiffUtils) {

    inner class PendingRentPayViewHolder(
        val binding: PendingPayItemBinding
    ): ViewHolder(binding.root) {
        fun bind(state: PendingRentPayUI) {
            binding.state = state
            binding.listener = listener
            binding.executePendingBindings()

            Log.d("insideViewHolder", "holderPOsition: $adapterPosition")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingRentPayViewHolder {
        return PendingRentPayViewHolder(PendingPayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PendingRentPayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object DiffUtils: DiffUtil.ItemCallback<PendingRentPayUI>() {
        override fun areItemsTheSame(
            oldItem: PendingRentPayUI,
            newItem: PendingRentPayUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PendingRentPayUI,
            newItem: PendingRentPayUI
        ): Boolean {
            return oldItem.checked == newItem.checked && oldItem.enabled == newItem.enabled
        }
    }
}