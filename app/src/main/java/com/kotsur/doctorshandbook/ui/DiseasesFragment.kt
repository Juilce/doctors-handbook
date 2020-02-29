package com.kotsur.doctorshandbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.disease_item_view.view.*
import kotlinx.android.synthetic.main.fragment_diseases.*

class DiseasesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diseases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_disease.setOnClickListener {
            AddDiseaseFragment() {
                updateDiseases()
            }.show(childFragmentManager, "AddDiseaseFragment")
        }

        updateDiseases()
    }

    private fun updateDiseases() {
        diseases_container.removeAllViews()

        for (disease in Data.diseases) {

            val diseaseItemView = layoutInflater.inflate(R.layout.disease_item_view, null)

            diseaseItemView.name.text = disease.name
            diseaseItemView.delete.setOnClickListener {
                Data.diseases.remove(disease)
                diseases_container.removeView(diseaseItemView)
            }
            diseases_container.addView(diseaseItemView)
        }
    }
}