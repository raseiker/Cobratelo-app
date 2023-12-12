package com.example.cobratelo_app.ui.pay_history.pay_confirmation.rent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.databinding.SelectedPaysItemBinding

class RentPayConfirmationAdapter: ListAdapter<RentPayConfirmationUI, RentPayConfirmationAdapter.PayConfirmationViewHolder>(
    DiffCallback
) {

    inner class PayConfirmationViewHolder(
        private val binding: SelectedPaysItemBinding
    ): ViewHolder(binding.root) {
        fun bind(item: RentPayConfirmationUI) {
            binding.stateUI = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayConfirmationViewHolder {
        return PayConfirmationViewHolder(SelectedPaysItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PayConfirmationViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<RentPayConfirmationUI>() {
        override fun areItemsTheSame(
            oldItem: RentPayConfirmationUI,
            newItem: RentPayConfirmationUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RentPayConfirmationUI,
            newItem: RentPayConfirmationUI
        ): Boolean {
            return oldItem.date == newItem.date
                    && oldItem.amount == newItem.amount
                    && oldItem.concept == newItem.concept
        }
    }
}