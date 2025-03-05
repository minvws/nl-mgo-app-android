# Data - FHIR Parser

This module provides classes for handling [FHIR (Fast Healthcare Interoperability Resources)](https://www.hl7.org/fhir/).  
It executes functions from a JavaScript file to parse FHIR resources into a custom domain model,  
which can then be used to display healthcare-related data.

The javascript function `getMgoResourceJson` parses a FHIR response that returns a 
`MgoResource`. This is a data class mapped from the FHIR response to turn it 
into something more 
readable and easier to access. This class it not used to display health care related data. To do 
so, call `getDetailsJson/getSummaryJson` which returns a `HealthUiSchema`.

## Highlights

- **`DefaultJsRuntimeRepository`** – A wrapper class for executing JavaScript functions from an external script using J2V8.  
  It ensures that function calls are managed efficiently within the application's coroutine framework.

- **`DefaultMgoResourceMapper`** – Maps the JSON response from the JavaScript function to a 
  `MgoResource`
  This domain model serves as an intermediary for transforming data into a UI-friendly format.

- **`DefaultUiSchemaMapper`** – Converts an `MgoResource` into a `HealthUiSchema`  
  which is then used to generate UI components that display healthcare-related data.
