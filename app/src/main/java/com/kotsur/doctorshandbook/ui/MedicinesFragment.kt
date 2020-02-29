package com.kotsur.doctorshandbook.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Medicine
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_medicines.*
import kotlinx.android.synthetic.main.new_medicine_layout.view.*

class MedicinesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        new_medicine.setOnClickListener {


            val newMedicineView = layoutInflater.inflate(R.layout.new_medicine_layout, null)

            AlertDialog.Builder(requireContext())
                .setTitle("New medicine")
                .setView(newMedicineView)
                .setPositiveButton("Save") { dialog, position ->
                    val selectedName =
                        newMedicineView.medicine_name.editText?.text?.toString() ?: ""

                    // TODO Y: check that name is not empty

                    Data.medicines.add(Medicine(name = selectedName))
                    updateList()
                    dialog.dismiss()
                }
                .show()

        }

        updateList()
    }

    private fun updateList() {
        medicines_container.removeAllViews()

        for (medicine in Data.medicines) {
            val medicineView = TextView(requireContext())
            medicineView.text = "~ ${medicine.name} ~"
            medicineView.setPadding(10, 10, 10, 10)
            medicines_container.addView(medicineView)
        }
    }
}