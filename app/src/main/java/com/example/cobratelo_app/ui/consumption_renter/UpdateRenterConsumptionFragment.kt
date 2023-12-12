package com.example.cobratelo_app.ui.consumption_renter

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.generateMenuDropdown
import com.example.cobratelo_app.core.showDatePicker
import com.example.cobratelo_app.core.validateFields
import com.example.cobratelo_app.databinding.FragmentUpdateRenterConsumptionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateRenterConsumptionFragment : Fragment() {

    private val viewModel: UpdateRenterConsumptionViewModel by viewModels()
    private var binding: FragmentUpdateRenterConsumptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateRenterConsumptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
//            viewModel = this@UpdateRenterConsumptionFragment.viewModel

            //validateFields(listOf(tvKwh, tvRentKwhConsumptionDate, tvWater, rentersTv), updateBtn)

            rentKwhConsumptionDate.setEndIconOnClickListener {
                //show date picker
                showDatePicker(tvRentKwhConsumptionDate)
            }

            //create name list of existing renters
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    generateMenuDropdown(renters, viewModel.getAllRenterNames().await())
                    validateFields(listOf(tvKwh, tvRentKwhConsumptionDate, tvWater, rentersTv), updateBtn)
                }
            }

            //click listener
            updateBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        val response = updateRenterConsumption (
                            rentersTv.text.toString(),
                            kwhTxt.editText?.text.toString(),
                            waterTxt.editText?.text.toString(),
                            rentKwhConsumptionDate.editText?.text.toString()
                        )

                        //if true then show a confirmation message, if false show a error message
                        if (response.await()) {
                            //show confirmation message
                            showConfirmationToast(rentersTv.text.toString())

                            //clean fields
                            cleanFields()
                        } else {
                            //show error
                        }
                    }
                }

            }
        }
    }

    private fun cleanFields() = binding?.apply {
        rentersTv.text = Editable.Factory.getInstance().newEditable("")
        kwhTxt.editText?.text = Editable.Factory.getInstance().newEditable("")
        waterTxt.editText?.text = Editable.Factory.getInstance().newEditable("")
        rentKwhConsumptionDate.editText?.text =
            Editable.Factory.getInstance().newEditable("")
    }

    private fun updateRenterConsumption(
        renterName: String,
        kwh: String,
        water: String,
        date: String
    ) =
        viewModel.updateRenterConsumption(renterName, kwh, water, date)

    private fun showConfirmationToast(renterName: String) {
        Toast.makeText(
            requireContext(),
            "successfully update consumption for $renterName",
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}