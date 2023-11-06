package com.example.cobratelo_app.ui.binding_adapter

import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cobratelo_app.R
import com.example.cobratelo_app.data.model.Renter
import com.example.cobratelo_app.data.model.getRentPaymentDay
import com.example.cobratelo_app.data.model.getServicesPaymentDay
import com.example.cobratelo_app.ui.home.adapter.RenterAdapter
import com.example.cobratelo_app.ui.pay_history.rent.CanceledRentPayAdapter
import com.example.cobratelo_app.ui.pay_history.rent.CanceledRentPayUI
import com.example.cobratelo_app.ui.pay_history.rent.PendingRentPayAdapter
import com.example.cobratelo_app.ui.pay_history.rent.PendingRentPayUI
import com.example.cobratelo_app.ui.pay_history.service.CanceledServicePayUI
import com.example.cobratelo_app.ui.pay_history.service.CanceledServiceRentPayAdapter
import com.example.cobratelo_app.ui.pay_history.service.PendingServicePayAdapter
import com.example.cobratelo_app.ui.pay_history.service.PendingServicePayUI

@BindingAdapter("dataList")
fun RecyclerView.bindData(data: List<Renter>?) {
    data?.let {list ->
        val adapter = adapter as RenterAdapter
        adapter.submitList(list)
    }
}

@BindingAdapter("rentPaymentDay")
fun TextView.bindRentDate(renter: Renter?) {
    renter?.let {
        this.text = context.getString(R.string.renter_rent_payment, renter.getRentPaymentDay())
    }
}

@BindingAdapter("servicePaymentDay")
fun TextView.bindServiceDate(renter: Renter?) {
    renter?.let {
        this.text = context.getString(R.string.renter_services_payment, renter.getServicesPaymentDay())
    }
}

@BindingAdapter("dataList")
fun RecyclerView.bindPendingRentPayData(data: List<PendingRentPayUI>?) {
    data?.let {list ->
        val adapter = adapter as PendingRentPayAdapter
        adapter.submitList(list)
    }
}

@BindingAdapter("dataList")
fun RecyclerView.bindCanceledRentPayData(data: List<CanceledRentPayUI>?) {
    data?.let {list ->
        val adapter = adapter as CanceledRentPayAdapter
        adapter.submitList(list)
    }
}

@BindingAdapter("dataList")
fun RecyclerView.bindPendingServicePayData(data:List<PendingServicePayUI>?){
    data?.let {list ->
        val adapter = adapter as PendingServicePayAdapter
        adapter.submitList(list)
    }
}

@BindingAdapter("dataList")
fun RecyclerView.bindCanceledServicePayData(data: List<CanceledServicePayUI>?){
    data?.let { list ->
        val adapter = adapter as CanceledServiceRentPayAdapter
        adapter.submitList(list)
    }
}
