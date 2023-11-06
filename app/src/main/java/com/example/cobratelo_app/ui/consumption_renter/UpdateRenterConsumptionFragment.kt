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
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.R
import com.example.cobratelo_app.core.generateMenuDropdown
import com.example.cobratelo_app.core.showDatePicker
import com.example.cobratelo_app.core.validateFields
import com.example.cobratelo_app.databinding.FragmentUpdateRenterConsumptionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

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

            validateFields(listOf(tvKwh, tvRentKwhConsumptionDate, tvWater, rentersTv), updateBtn)

            rentKwhConsumptionDate.setEndIconOnClickListener {
                //show date picker
                showDatePicker(tvRentKwhConsumptionDate)
            }

            //create name list of existing renters
            generateMenuDropdown(renters, viewModel.getAllRenterNames())

            //click listener
            updateBtn.setOnClickListener {
                val response = updateRenterConsumption(
                    rentersTv.text.toString(),
                    kwhTxt.editText?.text.toString(),
                    waterTxt.editText?.text.toString(),
                    rentKwhConsumptionDate.editText?.text.toString()
                )

                //if true then show a confirmation message, if false show a error message
                if (response) {
                    //show confirmation message
                    showConfirmationToast(rentersTv.text.toString())

                    //clean fields
                    rentersTv.text = Editable.Factory.getInstance().newEditable("")
                    kwhTxt.editText?.text = Editable.Factory.getInstance().newEditable("")
                    waterTxt.editText?.text = Editable.Factory.getInstance().newEditable("")
                    rentKwhConsumptionDate.editText?.text = Editable.Factory.getInstance().newEditable("")
                } else {
                    //show error
                }
            }
        }
    }

    private fun updateRenterConsumption(renterName: String, kwh: String, water: String, date: String) =
        viewModel.updateRenterConsumption(renterName, kwh, water, date)

    private fun showConfirmationToast(renterName: String) {
        Toast.makeText(requireContext(), "successfully update consumption for $renterName", Toast.LENGTH_LONG)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}