package com.kotsur.doctorshandbook

data class Disease(
    val name: String,
    val symptoms: String,
    val procedure: String,
    val medicine: List<PrescriptionMedicine>
)

data class Medicine(val name: String)
data class Warehouse(val medicene: Medicine, val amount: Int, val replacement: Medicine)
data class PrescriptionMedicine(val medicine: Medicine, val count: Int, val prescription: String)
data class Prescription(
    val patient: String,
    val disease: Disease,
    val medicine: List<PrescriptionMedicine>
)

object Data {
    val diseases: MutableMap<String, Disease> = mutableMapOf()
    val medicines: MutableMap<String, Medicine> = mutableMapOf()
    val warehouse: MutableSet<Medicine> = mutableSetOf()
    val prescriptions: MutableList<Prescription> = mutableListOf()

    init {
        val advilMedicine = Medicine(name = "Advil")
        val advilPrescriptionMedicine = PrescriptionMedicine(
            medicine = advilMedicine,
            count = 2,
            prescription = "Take 2 pills 3 times a day with food"
        )
        medicines["advil"] = advilMedicine
        val measlesDisease = Disease(
            name = "measles", symptoms = "", procedure = "", medicine = listOf(
                advilPrescriptionMedicine
            )
        )
        diseases["measles"] = measlesDisease
        val andrewPrescription = Prescription(
            patient = "Andrew", disease = measlesDisease,
            medicine = listOf(advilPrescriptionMedicine)
        )
        prescriptions.add(andrewPrescription)
    }
}