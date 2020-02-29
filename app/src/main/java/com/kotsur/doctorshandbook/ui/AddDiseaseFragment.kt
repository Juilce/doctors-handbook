package com.kotsur.doctorshandbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Disease
import com.kotsur.doctorshandbook.PrescriptionMedicine
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_add_disease.*
import kotlinx.android.synthetic.main.fragment_add_disease.view.*

class AddDiseaseFragment(
    private val onAddDiseaseCompleted: () -> Unit
) : DialogFragment() {

    private val selectedMedicines = mutableListOf<PrescriptionMedicine>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_disease, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_prescription_medicine.setOnClickListener {
            AddPrescriptionMedicineFragment() { selectedPrescription ->
                selectedMedicines.add(selectedPrescription)
                updateMedicinesList()
            }.show(childFragmentManager, "AddPrescriptionMedicineFragment")
        }

        clear_medicine.setOnClickListener {
            selectedMedicines.clear()
            updateMedicinesList()
        }

        save.setOnClickListener {
            saveAndExit()
        }

    }

    private fun saveAndExit() {
        // TODO Yulia add checks!

        val selectedName = name.editText?.text?.toString() ?: ""
        val selectedSymptoms = symptoms.editText?.text?.toString() ?: ""
        val selectedProcedures = procedures.editText?.text?.toString() ?: ""

        val newDisease = Disease(
            name = selectedName,
            procedure = selectedProcedures,
            symptoms = selectedSymptoms,
            medicine = selectedMedicines
        )

        Data.diseases.add(newDisease)
        onAddDiseaseCompleted()
        dismiss()
    }

    private fun updateMedicinesList() {
        medicine_container.removeAllViews()

        for (prescriptionMedicine in selectedMedicines) {
            val prescriptionMedicineView =
                createPrescriptionMedicineView(layoutInflater, prescriptionMedicine)
            medicine_container.addView(prescriptionMedicineView)
        }
    }

}