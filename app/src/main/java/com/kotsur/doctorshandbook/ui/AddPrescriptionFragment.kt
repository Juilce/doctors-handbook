package com.kotsur.doctorshandbook.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.kotsur.doctorshandbook.*
import kotlinx.android.synthetic.main.fragment_add_prescription.*
import kotlinx.android.synthetic.main.prescription_medicine_item.view.*

class AddPrescriptionFragment : DialogFragment() {

    private var selectedDisease: Disease? = null
    private val selectedPrescriptionMedicine: MutableSet<PrescriptionMedicine> = mutableSetOf()

    var onCompletedListener: () -> Unit = { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_prescription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select_medicine.setOnClickListener {

            val disease = this.selectedDisease
            if (disease == null) {
                Toast.makeText(requireContext(), "Select the disease first!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val itemsForSelection = Array<CharSequence>(disease.medicine.size) { index ->
                disease.medicine[index].medicine.name
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Select medicine for ${disease.name}")
                .setItems(itemsForSelection) { dialog, selectedIndex ->
                    val prescriptionMedicine = disease.medicine[selectedIndex]
                    selectedPrescriptionMedicine.add(prescriptionMedicine)
                    showSelectedMedicine()
                    dialog.dismiss()
                }.show()

        }

        clear_medicine.setOnClickListener {
            selectedPrescriptionMedicine.clear()
            medicine_list.removeAllViews()
        }

        disease_button.setOnClickListener {

            val diseases: MutableList<Disease> = mutableListOf()

            for (entry: Map.Entry<String, Disease> in Data.diseases) {
                diseases.add(entry.value)
            }

            val itemsForSelection = Array<CharSequence>(diseases.size) { index ->
                diseases[index].name
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Select disease")
                .setItems(itemsForSelection) { dialog, selectedIndex ->
                    dialog.dismiss()
                    disease_button.text = diseases[selectedIndex].name
                    selectedDisease = diseases[selectedIndex]
                }.show()

        }

        save_prescription.setOnClickListener {

            val patientName = patient_name.editText?.text.toString()
            if (patientName.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Patient name should not be empty!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val selectedDisease = this.selectedDisease
            if (selectedDisease == null || selectedPrescriptionMedicine.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, select the disease and medicine!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val newPrescription = Prescription(
                patient = patientName,
                disease = selectedDisease,
                medicine = selectedPrescriptionMedicine.toList()
            )

            Data.prescriptions.add(newPrescription)

            dismiss()
            onCompletedListener()
        }
    }

    private fun showSelectedMedicine() {
        medicine_list.removeAllViews()

        for (prescriptionMedicine in selectedPrescriptionMedicine) {
            val selectedMedicineView =
                layoutInflater.inflate(R.layout.prescription_medicine_item, null)
            selectedMedicineView.medicine.text =
                "${prescriptionMedicine.medicine.name} count: ${prescriptionMedicine.count}"
            selectedMedicineView.prescription.text = prescriptionMedicine.prescription
            medicine_list.addView(selectedMedicineView)
        }
    }
}