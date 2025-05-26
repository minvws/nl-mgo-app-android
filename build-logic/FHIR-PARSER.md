# Build Logic - Plugins - FHIR Parser

The **FhirParserPlugin** automates handling FHIR-related data handling shared across Web, iOS, and
Android platforms.

## Setup

The plugin requires the `MGO_GITHUB_PAT` environment variable to be set.
You can generate this Personal Access Token (PAT) in GitHub by navigating to:
Settings → Developer settings → Personal access tokens → Tokens (classic).
Make sure to grant the token both `repo` and `workflow` permissions.

## Run

```sh
./gradlew updateFhirParser
```

## Workflow

The plugin executes in four steps:

1. **[Download files](#step-1-download-files)**
2. **[Move files](#step-2-move-files)**
3. **[Modify JSON Schema](#step-3-modify-json-schema)**
4. **[Generate Kotlin classes](#step-4-generate-kotlin-classes)**
5. **[Modify generated classes](#step-5-modify-generated-classes)**

---

## Step 1: Download Files

Each time the Web team updates the shared library, GitHub Actions generates an artifact
containing the necessary files. The first step:

1. Downloads the artifact.
2. Extracts the files.

The files that are downloaded are:

- **mgo-fhir-data.iife.js** → The Javascript file that contains functions to handle FHIR data.
- **types.json** -> JSON Schema generated from Typescript. Used to generate Kotlin classes with
  [Json Kotlin Schema Codegen](https://github.com/pwall567/json-kotlin-schema-codegen).
- **version.json** -> Defines the version of the shared code.

---

## Step 2: Move Files

The downloaded **mgo-fhir-data.iife.js** and **version.json** files are moved to the correct module.

## Step 3: Modify JSON Schema

The Web-generated `types.json` file requires adjustments before it can generate the proper Kotlin
classes we want.

### Rename `anyOf` to `oneOf`

The [Json Kotlin Schema Codegen](https://github.com/pwall567/json-kotlin-schema-codegen) library
supports polymorphism but requires `oneOf` instead of `anyOf`. This enables correct interface and
class generation.

### Adjust Nested `oneOf`

This library requires nested `oneOf` objects in a specific format. Below is the transformation:

#### **Original Schema:**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "Car": {
      "type": "object",
      "properties": {
        "model": {
          "type": "string"
        }
      }
    },
    "Motorcycle": {
      "type": "object",
      "properties": {
        "model": {
          "type": "string"
        }
      }
    },
    "Vehicle": {
      "type": "object",
      "properties": {
        "type": {
          "type": "array",
          "items": {
            "oneOf": [
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

#### **Modified Schema:**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "VehicleType": {
      "oneOf": [
        {
          "$ref": "#/definitions/Car"
        },
        {
          "$ref": "#/definitions/Motorcycle"
        }
      ]
    },
    "Car": {
      "type": "object",
      "properties": {
        "model": {
          "type": "string"
        }
      }
    },
    "Motorcycle": {
      "type": "object",
      "properties": {
        "model": {
          "type": "string"
        }
      }
    },
    "Vehicle": {
      "type": "object",
      "properties": {
        "type": {
          "type": "array",
          "items": {
            "$ref": "#/definitions/VehicleType"
          }
        }
      }
    }
  }
}
```

#### **Kotlin Classes:**

```kotlin
sealed interface Vehicle
data class Car(val model: String) : Vehicle
data class Motorcycle(val model: String) : Vehicle
```

### Add `default` field next to `const` property

The schema requires a `default` field next to `const` for it to set the default value of a variable.

#### **Original Schema:**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "ZibMedicationUse": {
      "type": "object",
      "properties": {
        "profile": {
          "const": "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse",
          "type": "string"
        }
      }
    }
  }
}
```

#### **Modified Schema:**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "ZibMedicationUse": {
      "type": "object",
      "properties": {
        "profile": {
          "const": "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse",
          "default": "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse",
          "type": "string"
        }
      }
    }
  }
}
```

#### **Kotlin Class:**

```kotlin
data class ZibMedicationUse(val profile: String = "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse")
```

### Create `Profiles` Object

To simplify accessing profile strings, we extract all profile strings into a `Profiles` class:

#### **Schema:**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "Profiles": {
      "type": "object",
      "properties": {
        "zibMedicationUse": {
          "const": "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse",
          "type": "string"
        }
      }
    }
  }
}
```

#### **Kotlin Class:**

```kotlin
data class Profiles(
    val zibMedicationUse: String = "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse"
)
```

---

## Step 4: Generate Kotlin Classes

After modifying `types.json`, we
use [Json Kotlin Schema Codegen](https://github.com/pwall567/json-kotlin-schema-codegen) to generate
Kotlin classes.

---

## Step 5: Modify Generated Classes

Once the Kotlin classes are generated, we apply three modifications:

- **Make interfaces sealed** → Ensures compile-time safety for known implementations.
- **Add `@Serializable` annotation** → Enables KotlinX Serialization support.
- **Make `Profiles` a static object** → Since it only contains constant values, making it static
  makes it easier to access.

