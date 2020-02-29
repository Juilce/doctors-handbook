package com.kotsur.doctorshandbook.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotsur.doctorshandbook.Data
import com.kotsur.doctorshandbook.Medicine
import com.kotsur.doctorshandbook.R
import kotlinx.android.synthetic.main.fragment_warehouse.*
import kotlinx.android.synthetic.main.new_warehouse_item_layout.view.*
import kotlinx.android.synthetic.main.warehouse_item.view.*
import kotlinx.android.synthetic.main.warehouse_item.view.balance

class WarehouseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_warehouse, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_warehouse_item.setOnClickListener {

            val newBalanceItemView = layoutInflater.inflate(R.layout.new_warehouse_item_layout, null)


            var selectedMedicine: Medicine? = null
            newBalanceItemView.select_medicine.setOnClickListener {

                val itemsToSelect = Array<CharSequence>(Data.medicines.size) { position ->
                    "- ${Data.medicines[position].name} -"
                }

                AlertDialog.Builder(requireContext())
                    .setTitle("Select medicine")
                    .setItems(itemsToSelect){ dialog, selectedPosition ->
                        selectedMedicine = Data.medicines[selectedPosition]
                        newBalanceItemView.select_medicine.text = itemsToSelect[selectedPosition]
                        dialog.dismiss()
                    }
                    .show()

            }

            AlertDialog.Builder(requireContext())
                .setView(newBalanceItemView)
                .setPositiveButton("Done"){dialog, pos ->

                    val medicine = selectedMedicine
                    if (medicine == null){
                        // TODO show toast
                        return@setPositiveButton
                    }

                    val selectedCount = newBalanceItemView.medicine_balance.editText?.text?.toString()?.toInt()?:0
                    // TODO check count is not 0

                    // update global data
                    Data.warehouse[medicine] = selectedCount
                    updateList()
                    dialog.dismiss()
                }
                .show()

        }

        updateList()
    }

    private fun updateList() {
        warehouse_container.removeAllViews()

        for ((medicine, count) in Data.warehouse) {
            val warehouseItemView = layoutInflater.inflate(R.layout.warehouse_item, null)

            warehouseItemView.medicine.text = medicine.name
            warehouseItemView.balance.text = "$count"

            warehouse_container.addView(warehouseItemView)
        }
    }

}