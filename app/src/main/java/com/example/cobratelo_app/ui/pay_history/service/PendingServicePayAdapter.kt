package com.example.cobratelo_app.ui.pay_history.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.databinding.ServicePayItemBinding

class PendingServicePayAdapter(
    private val listener: PendingServicePayListener
): ListAdapter<PendingServicePayUI, PendingServicePayAdapter.PendingServicePayViewHolder>(DiffCallback) {

    inner class PendingServicePayViewHolder(
        private val binding: ServicePayItemBinding
    ): ViewHolder(binding.root){
        fun bind(currentItem: PendingServicePayUI?) {
            binding.state = currentItem
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingServicePayViewHolder {
        return PendingServicePayViewHolder(
            ServicePayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PendingServicePayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<PendingServicePayUI>(){
        override fun areItemsTheSame(
            oldItem: PendingServicePayUI,
            newItem: PendingServicePayUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PendingServicePayUI,
            newItem: PendingServicePayUI
        ): Boolean {
            return oldItem.amount == newItem.amount
                    && oldItem.concept == newItem.concept
                    && oldItem.checked == newItem.checked
                    && oldItem.enabled == newItem.enabled
        }
    }
}