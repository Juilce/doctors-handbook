package com.kotsur.doctorshandbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

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

            val newPrescription = Prescription(patient = "New", disease = Data.diseases["measles"]!!, medicine = emptyList())
            Data.prescriptions.add(newPrescription)

            showPrescriptions()
        }

        showPrescriptions()
    }

    private fun showPrescriptions() {
        prescrption_list.removeAllViews()
        val prescriptions = Data.prescriptions
        for (prescription in prescriptions) {
            val prescriptionView = TextView(requireContext())
            prescriptionView.text =
                "Patient: ${prescription.patient}, Disease: ${prescription.disease.name}"
            prescrption_list.addView(prescriptionView)
        }
    }


}
