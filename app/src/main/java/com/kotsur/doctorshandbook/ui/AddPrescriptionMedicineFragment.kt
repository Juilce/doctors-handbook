package com.kotsur.doctorshandbook.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Medicine
import com.kotsur.doctorshandbook.PrescriptionMedicine
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_add_prescription_medicine.*

class AddPrescriptionMedicineFragment(
    private val onPrescriptionReady: (PrescriptionMedicine) -> Unit
) : DialogFragment() {

    private var selectedMedicine: Medicine? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_prescription_medicine, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select_medicine.setOnClickListener {

            val itemsToSelect = Array<CharSequence>(Data.medicines.size) { position ->
                "- ${Data.medicines[position].name} -"
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Select medicine")
                .setItems(itemsToSelect) { dialog, selectedPosition ->
                    selectedMedicine = Data.medicines[selectedPosition]
                    select_medicine.text = itemsToSelect[selectedPosition]
                    dialog.dismiss()
                }.show()
        }

        save.setOnClickListener {

            val medicine = selectedMedicine
            if (medicine == null) {
                Toast.makeText(
                    requireContext(),
                    "Please, select the medicine!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val selectedCount = count.editText?.text?.toString()?.toInt() ?: 0
            if (selectedCount == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please, enter the count!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val selectedPrescription = prescription.editText?.text?.toString() ?: ""
            if (selectedPrescription.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Please, enter the prescription!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val prescriptionMedicine = PrescriptionMedicine(
                medicine = medicine,
                count = selectedCount,
                prescription = selectedPrescription
            )

            onPrescriptionReady(prescriptionMedicine)
            dismiss()
        }


    }

}