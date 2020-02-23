package com.kotsur.doctorshandbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Prescription
import com.kotsur.doctorshandbook.R
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
            findNavController().navigate(R.id.add_prescription_fragment)
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
