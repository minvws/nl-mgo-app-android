package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.shared.DisplayElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementDisplay

internal fun UIElementDisplay?.getString(): String {
    return when (this) {
        is UIElementDisplay.StringValue -> this.value
        is UIElementDisplay.UnionArrayValue -> this.value.joinToString(", ") { it.getString() }
        else -> ""
    }
}

internal fun DisplayElement.getString(): String {
    return when (this) {
        is DisplayElement.StringValue -> this.value
        is DisplayElement.StringArrayValue -> this.value.joinToString(", ")
    }
}
