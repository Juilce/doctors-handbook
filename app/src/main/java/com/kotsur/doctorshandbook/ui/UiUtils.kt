package com.kotsur.doctorshandbook.ui

import android.view.LayoutInflater
import android.view.View
import com.kotsur.doctorshandbook.PrescriptionMedicine
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.prescription_medicine_item.view.*

fun createPrescriptionMedicineView(
    layoutInflater: LayoutInflater,
    prescriptionMedicine: PrescriptionMedicine
): View {
    val selectedMedicineView = layoutInflater.inflate(R.layout.prescription_medicine_item, null)
    selectedMedicineView.medicine.text =
        "${prescriptionMedicine.medicine.name} count: ${prescriptionMedicine.count}"
    selectedMedicineView.prescription.text = prescriptionMedicine.prescription
    return selectedMedicineView
}