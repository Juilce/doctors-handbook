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

class AddPrescriptionFragment : DialogFragment() {

    private var selectedDisease: Disease? = null
    private val selectedPrescriptionMedicineList: MutableSet<PrescriptionMedicine> = mutableSetOf()

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

                    val selectedPrescriptionMedicine: PrescriptionMedicine =
                        disease.medicine[selectedIndex]
                    val selectedMedicineCount: Int = selectedPrescriptionMedicine.count

                    val medicineBalance: Int =
                        Data.warehouse[selectedPrescriptionMedicine.medicine] ?: 0

                    if (selectedMedicineCount > medicineBalance) {

                        AlertDialog.Builder(requireContext())
                            .setTitle("Attention")
                            .setMessage(
                                "For selected medicine: ${selectedPrescriptionMedicine.medicine.name} and count: $selectedMedicineCount there is not enough balance on the warehouse!" +
                                        "Warehouse balance is $medicineBalance.\n Please select another medicine or count!"
                            )
                            .setPositiveButton("OK") { dialog, which ->

                            }
                            .show()

                        return@setItems
                    }

                    selectedPrescriptionMedicineList.add(selectedPrescriptionMedicine)
                    showSelectedMedicine()

                    dialog.dismiss()
                }.show()

        }

        clear_medicine.setOnClickListener {
            selectedPrescriptionMedicineList.clear()
            medicine_list.removeAllViews()
        }

        disease_button.setOnClickListener {

            val diseases: MutableList<Disease> = mutableListOf()

            for (entry: Disease in Data.diseases) {
                diseases.add(entry)
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
            if (selectedDisease == null || selectedPrescriptionMedicineList.isEmpty()) {
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
                medicine = selectedPrescriptionMedicineList.toList()
            )

            Data.prescriptions.add(newPrescription)

            dismiss()
            onCompletedListener()
        }
    }

    private fun showSelectedMedicine() {
        medicine_list.removeAllViews()

        for (prescriptionMedicine in selectedPrescriptionMedicineList) {
            val selectedMedicineView =
                createPrescriptionMedicineView(layoutInflater, prescriptionMedicine)
            medicine_list.addView(selectedMedicineView)
        }
    }

}


