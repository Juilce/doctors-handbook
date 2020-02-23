package com.kotsur.doctorshandbook.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.protobuf.MapEntryLite
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Disease
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_add_prescription.*

class AddPrescriptionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_prescription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disease.setOnClickListener {

            val diseases: List<Disease> = Data.diseases.map { entry: Map.Entry<String, Disease> -> entry.value }

            AlertDialog.Builder(requireContext())
                .setTitle("Select disease")
                .setItems(diseases.map { it.name }.toTypedArray()){ dialog, index ->
                    dialog.dismiss()
                    disease.text = diseases[index].name
                }.show()

        }
    }
}