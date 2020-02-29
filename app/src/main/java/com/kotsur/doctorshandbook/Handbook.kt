package com.kotsur.doctorshandbook

/**
 * Disease
 */
data class Disease(
    val name: String,
    val symptoms: String,
    val procedure: String,
    val medicine: List<PrescriptionMedicine>
)

/**
 * Medicine
 */
data class Medicine(val name: String)

data class Warehouse(
    val medicene: Medicine,
    val amount: Int,
    val replacement: Medicine
)

/**
 * PrescriptionMedicine -
 */
data class PrescriptionMedicine(
    val medicine: Medicine,
    val count: Int,
    val prescription: String
)

data class Prescription(
    val patient: String,
    val disease: Disease,
    val medicine: List<PrescriptionMedicine>
)

object Data {
    val diseases: MutableList<Disease> = mutableListOf()
    val medicines: MutableList<Medicine> = mutableListOf()
    val warehouse: MutableMap<Medicine, Int> = mutableMapOf()
    val prescriptions: MutableList<Prescription> = mutableListOf()

    init {
        addInitialData()
    }

    private fun addInitialData() {
        val advilMedicine = Medicine(name = "Advil")
        val advilPrescriptionMedicine = PrescriptionMedicine(
            medicine = advilMedicine,
            count = 3,
            prescription = "Take 2 pills 3 times a day with food"
        )
        medicines.add(advilMedicine)

        val measlesDisease = Disease(
            name = "measles", symptoms = "", procedure = "", medicine = listOf(
                advilPrescriptionMedicine
            )
        )
        diseases.add(measlesDisease)
        val andrewPrescription = Prescription(
            patient = "Andrew", disease = measlesDisease,
            medicine = listOf(advilPrescriptionMedicine)
        )
        prescriptions.add(andrewPrescription)

        // warehouse balance
        warehouse[advilMedicine] = 2
    }
}