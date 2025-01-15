package nl.rijksoverheid.mgo.data.fhirParser.js

class TestJsRuntimeRepository : JsRuntimeRepository {
    private var stringFunctionReturn: String = ""

    fun setStringFunctionReturn(stringReturn: String) {
        this.stringFunctionReturn = stringReturn
    }

    override suspend fun load() {
    }

    override suspend fun executeStringFunction(
        name: String,
        parameters: List<String>,
    ): String {
        return stringFunctionReturn
    }
}
