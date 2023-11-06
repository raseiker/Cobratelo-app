package com.example.cobratelo_app.ui.home.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.databinding.RenterItemRvBinding
import com.example.cobratelo_app.ui.home.HomeFragment
import com.example.cobratelo_app.ui.home.listener.HomeFragmentListener

class RenterAdapter(
    val homeFragment: HomeFragment,
    val listener: HomeFragmentListener
): ListAdapter<Renter, RenterAdapter.RenterViewHolder>(DiffCallback) {

    inner class RenterViewHolder(
        val binding: RenterItemRvBinding
    ): ViewHolder(binding.root) {
        fun bind(renter: Renter) {
            binding.fragment = homeFragment
            binding.listener = listener
            binding.renter = renter
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenterViewHolder {
        return RenterViewHolder(RenterItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RenterViewHolder, position: Int) {
        val currentRenter = getItem(position)
        holder.bind(currentRenter)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Renter>() {
        override fun areItemsTheSame(oldItem: Renter, newItem: Renter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Renter, newItem: Renter): Boolean {
            return oldItem.name == newItem.name
        }

    }
}