# Build Logic - Plugins - Fhir Parser

The **FhirParserPlugin** automates the process of handling FHIR-related data shared between Web, iOS, and Android platforms. It ensures that:

- The `mgo-fhir-data.iife.js` file is downloaded, containing shared functions.
- The `types.json` file, a JSON schema, is downloaded and used to generate Kotlin classes.
- The `version.json` file, which specifies the version of the shared code, is retrieved.

---

## Workflow
The plugin operates in four main steps:

1. **[Download files](#step-1-download-files)**
2. **[Modify `types.json`](#step-2-modify-typesjson)**
3. **[Generate Kotlin classes](#step-3-generate-kotlin-classes-from-typesjson)**
4. **[Modify generated classes](#step-4-modify-generated-classes)**

---

## Step 1: Download Files

Each time the Web team updates the shared library, GitHub Actions generates an artifact containing the required files. The plugin:

- Downloads the artifact.
- Extracts the files.
- Moves them to the appropriate module.

---

## Step 2: Modify `types.json`

The Web-generated `types.json` file requires adjustments before it can be used to generate 
Kotlin classes that we want.

### Rename `anyOf` to `oneOf`

The [Json Kotlin Schema Codegen](https://github.com/pwall567/json-kotlin-schema-codegen) library 
supports polymorphism, but it requires `oneOf` instead of `anyOf`. This allows for proper 
interface and class generation.

### Adjustments to nested `oneOf`

The [Json Kotlin Schema Codegen](https://github.com/pwall567/json-kotlin-schema-codegen) 
requires nested `oneOf` objects to be declared in a specific format so it can properly understand polymorphism. 
See 
below 
for the changes 
that are done to the original schema.

**Original Schema:**
```json
{
    "$schema": "http://json-schema.org/draft-07/schema#",
    "definitions":
    {
        "Car":
        {
            "type": "object",
            "properties":
            {
                "model":
                {
                    "type": "string"
                }
            }
        },
        "Motorcycle":
        {
            "type": "object",
            "properties":
            {
                "model":
                {
                    "type": "string"
                }
            }
        },
        "Vehicle":
        {
            "type": "object",
            "properties":
            {
                "type":
                {
                    "type": "array",
                    "items":
                    {
                        "oneOf":
                        [
                            {
                                "$ref": "#/definitions/Car"
                            },
                            {
                                "$ref": "#/definitions/Motorcycle"
                            }
                        ]
                    }
                }
            }
        }
    }
}
```

**Modified Schema:**
```json
{
    "$schema": "http://json-schema.org/draft-07/schema#",
    "definitions":
    {
        "VehicleType":
        {
            "oneOf":
            [
                {
                    "$ref": "#/definitions/Car"
                },
                {
                    "$ref": "#/definitions/Motorcycle"
                }
            ]
        },

        "Car":
        {
            "type": "object",
            "properties":
            {
                "model":
                {
                    "type": "string"
                }
            }
        },
        "Motorcycle":
        {
            "type": "object",
            "properties":
            {
                "model":
                {
                    "type": "string"
                }
            }
        },
        "Vehicle":
        {
            "type": "object",
            "properties":
            {
                "type":
                {
                    "type": "array",
                    "items":
                    {
                        "$ref": "#/definitions/VehicleType"
                    }
                }
            }
        }
    }
}
```

This will result in the following Kotlin classes:

```kotlin
interface Vehicle
data class Car(val model: String): Vehicle
data class Motorcycle(val model: String): Vehicle
```


