package com.example.cobratelo_app.ui.addRenter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cobratelo_app.core.CONTRACT_TIME_LIST
import com.example.cobratelo_app.core.FLAT_TYPE_LIST
import com.example.cobratelo_app.core.FLOOR_LIST
import com.example.cobratelo_app.core.MEMBERS_LIST
import com.example.cobratelo_app.core.PLACE_LIST
import com.example.cobratelo_app.core.generateMenuDropdown
import com.example.cobratelo_app.core.showDatePicker
import com.example.cobratelo_app.core.validateFields
import com.example.cobratelo_app.databinding.FragmentAddRenterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRenterFragment : Fragment() {


    private val viewModel by viewModels<AddRenterViewModel>()
    private var binding : FragmentAddRenterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRenterBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            val fields = listOf(
                tvName, tvRentAmount, tvRentDate, tvRentServicesDate,
                contractTimeTv, membersTv, placeTv, floorTv, flatTypeTv
            )

            //enable button accordingly
            validateFields(fields, addRenterBtn)

            generateMenuDropdown(contractTime, CONTRACT_TIME_LIST)
            generateMenuDropdown(members, MEMBERS_LIST)
            generateMenuDropdown(place, PLACE_LIST)
            generateMenuDropdown(place, PLACE_LIST)
            generateMenuDropdown(floor, FLOOR_LIST)
            generateMenuDropdown(flatType, FLAT_TYPE_LIST)

            rentDate.setEndIconOnClickListener {
                showDatePicker(tvRentDate)
            }

            rentServicesDate.setEndIconOnClickListener {
                showDatePicker(tvRentServicesDate)
            }

            //add new renter when click a button
            addRenterBtn.setOnClickListener {

                val response = addNewRenter(
                    tvName.text.toString().trim(),
                    tvRentAmount.text.toString().trim(),
                    tvRentDate.text.toString().trim(),
                    tvRentServicesDate.text.toString().trim(),
                    contractTimeTv.text.toString().trim(),
                    membersTv.text.toString().trim(),
                    placeTv.text.toString().trim(),
                    floorTv.text.toString().trim(),
                    flatTypeTv.text.toString().trim(),
                )

                if (response) {
                    //show confirmation dialog

                    //navigate back
                    findNavController().navigateUp()
                } else {
                    //show error
                }
            }
        }
    }

    /*
    Catch all fields and call a view model method
     */
    private fun addNewRenter(
        rentName: String,
        rentAmount: String,
        rentDate: String,
        rentServiceDate: String,
        rentContractTime: String,
        rentMembers: String,
        rentPlace: String,
        rentFloor: String,
        rentFlatType: String
    ) = viewModel.addRenter(rentName, rentAmount, rentDate, rentServiceDate, rentContractTime, rentMembers, rentPlace, rentFloor, rentFlatType)


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}