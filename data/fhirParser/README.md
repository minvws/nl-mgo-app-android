# Data - FHIR Parser

This module provides classes for handling [FHIR (Fast Healthcare Interoperability Resources)](https://www.hl7.org/fhir/).  
It executes functions from a JavaScript file to parse FHIR resources into a custom domain model,  
which is then used to display healthcare-related data in the application.

## Notable Files

- **`DefaultJsRuntimeRepository`** – A wrapper class for executing JavaScript functions from an external script using J2V8.  
  It ensures that function calls are managed efficiently within the application's coroutine framework.

- **`DefaultMgoResourceMapper`** – Maps the JSON response from the JavaScript function to a domain model.  
  This domain model serves as an intermediary for transforming data into a UI-friendly format.

- **`DefaultUiSchemaMapper`** – Converts an `MgoResource` into a structured domain model,  
  which is then used to generate UI components that display healthcare-related data. 
