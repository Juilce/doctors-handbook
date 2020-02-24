package com.kotsur.doctorshandbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.prescription_item.view.*

class PrescriptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_prescription.setOnClickListener {
            val addPrescriptionFragment = AddPrescriptionFragment()
            addPrescriptionFragment.onCompletedListener = {
                showPrescriptions()
            }
            addPrescriptionFragment.show(childFragmentManager, "AddPrescriptionFragment")
//            findNavController().navigate(R.id.add_prescription_fragment)
        }

        showPrescriptions()
    }

    private fun showPrescriptions() {
        prescrption_list.removeAllViews()

        val prescriptions = Data.prescriptions
        for (prescription in prescriptions) {
            val prescriptionView = layoutInflater.inflate(R.layout.prescription_item, null, false)
            prescriptionView.patient_name.text = prescription.patient
            prescriptionView.disease_button.text = prescription.disease.name

            prescriptionView.medicine_list.removeAllViews()
            for (prescriptionMedicine in prescription.medicine){
                val prescriptionMedicineView = TextView(requireContext())
                prescriptionMedicineView.text = "Medicine: ${prescriptionMedicine.medicine.name}, count: ${prescriptionMedicine.count} \n" +
                        "Prescription: ${prescriptionMedicine.prescription}"
                prescriptionMedicineView.setPadding(10, 10, 10, 10)
                prescriptionView.medicine_list.addView(prescriptionMedicineView)
            }

            prescrption_list.addView(prescriptionView)
        }
    }


}
