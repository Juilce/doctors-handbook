package com.kotsur.doctorshandbook

data class Disease(val name: String, val symptoms: String, val procedure: String, val medicine: List<DiseaseMedicine>)
data class Medicine(val name: String)
data class DiseaseMedicine(val medicine: Medicine, val count: Int)
data class Warehouse(val medicene: Medicine, val amount: Int, val replacement: Medicine)
data class PrescriptionMedicine(val medicine: Medicine, val count: Int)
data class Prescription(val patient: String, val disease: Disease, val medicine: List<PrescriptionMedicine>)

object Data {
    val diseases: MutableMap<String, Disease> = mutableMapOf()
    val medicines: MutableMap<String, Medicine> = mutableMapOf()
    val warehouse: MutableSet<Medicine> = mutableSetOf()
    val prescriptions: MutableList<Prescription> = mutableListOf()
    init{
        val advilMedicine = Medicine(name = "Advil")
        medicines["advil"] = advilMedicine
        val advilDiseaseMedicine = DiseaseMedicine(medicine = advilMedicine, count = 1)
        val measlesDisease = Disease(name = "measles", symptoms = "", procedure = "", medicine = listOf(
            advilDiseaseMedicine))
        diseases["measles"] = measlesDisease
        val advilPrescriptionMedicine = PrescriptionMedicine(medicine = advilMedicine, count = 2)
        val andrewPrescription = Prescription(patient = "Andrew", disease = measlesDisease,
            medicine = listOf(advilPrescriptionMedicine))
        prescriptions.add(andrewPrescription)
    }
}