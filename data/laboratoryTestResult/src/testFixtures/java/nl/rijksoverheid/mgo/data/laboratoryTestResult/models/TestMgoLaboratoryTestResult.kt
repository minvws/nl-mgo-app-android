package nl.rijksoverheid.mgo.data.laboratoryTestResult.models

val TEST_MGO_LABORATORY_TEST_RESULT =
    MgoLaboratoryTestResult(
        title = "Bevinding betreffende laboratoriumonderzoek (bevinding)",
        code = "Chloride [mol/volume] in bloed",
        status = "final",
        dateTime = "2012-05-23T12:00:00+02:00",
        result = "109 mmol/l",
        referenceRangeLow = "99 mmol/l",
        referenceRangeHigh = "108 mmol/l",
        interpretation = "boven referentiebereik (kwalificatiewaarde)",
        specimen = "Bloed (substantie)",
        collectionDateTime = "2012-05-23T08:08:00+02:00",
    )
