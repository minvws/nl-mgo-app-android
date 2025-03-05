# Data - HealthCare

This module provides classes for handling health care related data.

## Highlights

- **`CollectHealthCareDataStates`** Use to initialize fetching the health care data states. Fetches
  health care data based on the organizations that you added.
- **`DefaultHealthCareDataStatesRepository`** – Use to manage the health care data states. Can
  fetch, get, observe and delete health care data.
- **`DefaultMgoResourceRepository`** - Use to manage `MgoResource` objects. `MgoResource` represents
  the health care data, and are part of the `HealthCareDataState.Loaded` class.
- **`DefaultFhirBinaryRepository`** - Use to download FHIR (https://www.hl7.org/fhir/) binaries.
