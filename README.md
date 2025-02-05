# MGO - Android

## Table of contents
- [Introduction](#introduction)
- [Development](#development)
  - [Build Variants](#build-variants)
  - [Modules](#modules)
  - [UI](#ui)
    - [Previews](#previews)
  - [Dependency Injection](#dependency-injection)      
    - [Testing](#testing)
        - [JVM Tests](#jvm-tests)
        - [Android Tests](#android-tests)
        - [Snapshot Tests](#snapshot-tests)
  - [Copy](#copy)
  - [Custom gradle tasks](#custom-gradle-tasks)

## Introduction

This repository contains the Android release of the MGO project.

* The Android app is located in the repository you are currently viewing.
* The iOS app can also be [found on GitHub](https://github.com/minvws/nl-mgo-app-ios-private).

See minvws/*
*[nl-rdo-mgo-coordination-private](https://github.com/minvws/nl-rdo-mgo-coordination-private)** for
further technical documentation.
    
---   

## Development

To run the project, simply clone this repo and import it in Android Studio.

### Build variants

| Build Flavor | Description                                |  
|--------------|--------------------------------------------|  
| Tst          | Connects to backend test environment       |  
| Acc          | Connects to backend acceptance environment |  
| Prod         | Connects to backend production environment |  

To select a certain flavor select it in Android Studio from the "Build Variants" tab.

### Modules

This project is setup as a multi module project, with each module having it's own domain. See the
readme in each module for more information about that specific module.

The modules are grouped by the following folder structure:

| Group       | Description                                                                                         |  
|-------------|-----------------------------------------------------------------------------------------------------|  
| build-logic | Gradle plugins to share build logic between modules                                                 |  
| app         | Main entry point and orchestrator of the entire application                                         |  
| feature     | Standalone features that together make the application                                              |  
| data        | The data layer with backend integration and business logic                                          |  
| component   | Standalone UI components that can be used in various features                                       |  
| framework   | All other modules that do not fit any specific group, but contain specific standalone functionality |  

> Feature modules never depend on each other. Features are stand alone.

### UI

This project uses [Jetpack Compose](https://developer.android.com/develop/ui/compose) as the toolkit
for building UI in Android.

#### Previews

Each screen or component in the app
has [Previews](https://developer.android.com/develop/ui/compose/tooling/previews) included. These
previews reflect important states that can be quickly viewed from Android Studio without compiling
the app. Usually, these previews also act as snapshot tests.

### Dependency injection

This project
uses [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) as
Dependency Injection.

### Testing

#### JVM tests

All testing can be done using tests that are run on the jvm.
- If you don't need the Android platform, use [jUnit4](https://github.com/junit-team/junit4)
- If the Android platform is rquired, use [Robolectric](https://robolectric.org).

#### Android tests
For now, there is no need for Android Tests that require an Emulator. This is prefered, since these are slow tests and will have a pretty big impact on the CI.

#### Snapshot tests
Snapshot testing are created and verified with [Paparazzi](https://github.com/cashapp/paparazzi).

### Copy

Copy is stored in [Lokalise](https://lokalise.com/).

### Custom Gradle tasks

| Name                | Description                                |  
|-------------------- |--------------------------------------------|  
| updateCopy          | Updates the copy from Lokalise. Requires `MGO_LOKALISE_PROJECT_ID` and `MGO_LOKALISE_API_TOKEN` environment variables.       
| runCI               | Runs validation steps that are present on the CI as well. These steps include: `android linting`, `ktlint`, `verifying snapshot tests`, `jvm tests` and exporting `jacoco xml test report`.
| validateCodeCoverage | Exports `jacoco xml test report`, uploads the result to SonarQube and prints if our code coverage criteria is met (>=80%). Requires the `SONAR_TOKEN` environment variable.
| createPR            | Runs both the `runCI` and `validateCodeCoverage` steps. If all checks are okay, the browser is opened to create a PR.
| updateFhirParser    | Downloads the latest fhir parser release, moves and modifies it to run it in the Android project. Requires `MGO_GITHUB_PAT` environment variable.
