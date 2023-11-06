package com.example.cobratelo_app.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.RenterStatus
import com.example.cobratelo_app.databinding.FragmentHomeBinding
import com.example.cobratelo_app.ui.home.adapter.RenterAdapter
import com.example.cobratelo_app.ui.home.listener.HomeFragmentListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            lifecycleOwner = viewLifecycleOwner
            viewModel = this@HomeFragment.viewModel

            rentersRv.adapter = RenterAdapter(this@HomeFragment, HomeFragmentListener {renterId ->

                //send renterId to Renter Detail Fragment
                val action = HomeFragmentDirections.actionHomeFragmentToRenterDetailFragment(renterId)
                findNavController().navigate(action)
            })

            fabBtn.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addRenterFragment)
            }

            inflateMenu()
        }
    }

    /*
    This method replace onCreateOptionsMenu and onOptionsItemSelected methods that are deprecated
     */
    private fun inflateMenu() {
        requireActivity().addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_home_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_general_energy -> {
                        findNavController().navigate(R.id.action_homeFragment_to_energySummaryFragment)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun setStatusColor(status: Boolean): ColorStateList {
        return if (status) {
            ContextCompat.getColorStateList(requireContext(), R.color.uptodate_status)!!
        } else {
            ContextCompat.getColorStateList(requireContext(), R.color.pending_status)!!
        }
    }

    fun setTextStatus(status: Boolean): String {
        return if (status) RenterStatus.UpToDate.label else RenterStatus.Pending.label
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}