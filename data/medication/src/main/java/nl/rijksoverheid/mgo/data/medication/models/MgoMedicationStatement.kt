package nl.rijksoverheid.mgo.data.medication.models

import org.hl7.fhir.dstu3.model.MedicationStatement

data class MgoMedicationStatement(val name: String)

internal fun MedicationStatement.toMgoMedicationStatement(): MgoMedicationStatement {
    return MgoMedicationStatement(name = "Test")
}
