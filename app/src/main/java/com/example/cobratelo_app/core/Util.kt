package com.example.cobratelo_app.core

import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.cobratelo_app.R
import com.example.cobratelo_app.ui.addRenter.getPrettyCurrentDate
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun showDatePicker(inputEditText: TextInputEditText) {
    //create calendar object
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    //create date picker and show it
    val datePicker = DatePickerDialog(
        inputEditText.context,
        R.style.DialogTheme,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            //set date string as a input of the edit text
            inputEditText.setText("$d/${m + 1}/$y")
        }, year, month, day
    )

    //disable past days
    datePicker.datePicker.maxDate = calendar.timeInMillis

    datePicker.show()
}


fun getPlaceItems(inputLayout: TextInputLayout) {
    (inputLayout.editText as AutoCompleteTextView).setAdapter(ArrayAdapter(inputLayout.context, R.layout.list_item, PLACE_LIST))
}

fun generateMenuDropdown(inputLayout: TextInputLayout, list: List<String>?) {
    list?.let {
        (inputLayout.editText as AutoCompleteTextView).setAdapter(ArrayAdapter(inputLayout.context, R.layout.list_item, it))
    }
}

fun Int.getPrettyDay(): String {
    return if (this < 10) "0$this" else this.toString()
}

fun Int.getPrettyMonth() = when (this) {
    0 -> "ENERO"
    1 -> "FEBRERO"
    2 -> "MARZO"
    3 -> "ABRIL"
    4 -> "MAYO"
    5 -> "JUNIO"
    6 -> "JULIO"
    7 -> "AGOSTO"
    8 -> "SEPTIEMBRE"
    9 -> "OCTUBRE"
    10 -> "NOVIEMBRE"
    else -> "DICIEMBRE"
}

fun validateFields(fields: List<EditText>, btn: Button) = fields.forEach { editText ->
    editText.addTextChangedListener(onTextChanged = { _, _, _, _ ->
        btn.isEnabled = !fields.any { it.text.toString().isEmpty() }
    })
}

class Util {
//    val formatter = SimpleDateFormat("d/MM/yy", Locale.getDefault())
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/y", Locale.getDefault())
    val currentDate: LocalDate = LocalDate.now()
}