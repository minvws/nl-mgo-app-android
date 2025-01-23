# MGO - Android

## Introduction

This repository contains the Android release of the MGO project.

* The Android app is located in the repository you are currently viewing.
* The iOS app can also be [found on GitHub](https://github.com/minvws/nl-mgo-app-ios-private).

See minvws/*
*[nl-rdo-mgo-coordination-private](https://github.com/minvws/nl-rdo-mgo-coordination-private)** for
further technical documentation.
    
---   

## Development

To compile the project, import it in Android Studio and run it.

### Build Variants

| Build Flavor | Description                                |  
|--------------|--------------------------------------------|  
| Tst          | Connects to backend test environment       |  
| Acc          | Connects to backend acceptance environment |  
| Prod         | Connects to backend production environment |  
| Demo         | Temporary flavor for a demo (test env)     |

To run a certain flavor select it in Android Studio from the "Build Variants" tab. You can also
compile it using the following gradle command:

```  
./gradlew assemble[Flavor]Debug  
```  

### Custom gradle tasks

There are a couple of custom gradle tasks created to help with development. `./gradlew runCI` 
runs all the steps locally that are done by the CI as well, except for the code coverage report 
send to SonarQube. `./gradlew validateCodeCoverage` validates the code coverage report from 
SonarQube. `./gradlew createPR` runs all the CI steps locally, validates the code coverage report
and opens the browser to create the PR.

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

> Feature modules never depend on each other. Features are stand alone. Navigation between features
> happens in the app module.

### UI

This project uses [Jetpack Compose](https://developer.android.com/develop/ui/compose) as the toolkit
for building UI in Android.

#### Previews

Each screen or component in the app
has [Previews](https://developer.android.com/develop/ui/compose/tooling/previews) included. These
previews reflect important states that can be quickly viewed from Android Studio without compiling
the app. Usually, these previews also act as snapshot tests.

#### Snackbars

Custom Snackbars have been implemented to match with the design. To use Snackbars, use the
`MgoSnackbarScaffold` (instead of the normal `Scaffold`) where you want to Snackbar to show. To
display it, call the `show` method from the `SnackBarRepository`.

### Dependency Injection

This project
uses [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) as
Dependency Injection.

### Testing

#### End-to-end testing

End to end tests use a combination
of [AndroidJUnit4](https://developer.android.com/reference/androidx/test/ext/junit/runners/AndroidJUnit4),
[jUnit4](https://github.com/junit-team/junit4)
and [Compose Testing](https://developer.android.com/develop/ui/compose/testing).
These tests are located in: `app/src/androidTest/java/nl/rijksoverheid/mgo/endToEnd`.

#### Integration testing

Integration tests use a combination
of [AndroidJUnit4](https://developer.android.com/reference/androidx/test/ext/junit/runners/AndroidJUnit4), [jUnit4](https://github.com/junit-team/junit4)
and [Compose Testing](https://developer.android.com/develop/ui/compose/testing).
These tests are located in: `app/src/androidTest/java/nl/rijksoverheid/mgo/integration`.

#### Unit testing

Unit testing is performed using [jUnit4](https://github.com/junit-team/junit4).

#### Snapshot testing

Snapshot testing performed using  [Paparazzi](https://github.com/cashapp/paparazzi). For each screen
or component snapshot tests are created for:

- Phone portrait light mode
- Phone portrait light mode
- Phone portrait dark mode

### Copy

Copy is stored in [Lokalise](https://lokalise.com/). To update the copy from Lokalise, add the
`MGO_LOKALISE_PROJECT_ID` and `MGO_LOKALISE_API_TOKEN` environment variables. Then run the following
command to update the copy:

    ./gradlew updateCopy

Updating the copy is done in the editor on [Lokalise](https://lokalise.com/).

#### HTML

The app supports showing HTML. Currently `<b>` and `<a>` tags are supported. To show HTML text, use
the `MgoHtmlText` composable. Be sure that the string resource is wrapped with CDATA so that the
HTML is not stripped out.

> Lokalise automatically wraps HTML content in CDATA tags when it is exporting copy for Android.
> However, it only does this in certain scenarios (as per
> the [docs](https://docs.lokalise.com/en/articles/1400740-android-resources-xml)). To force CDATA,
> add the following custom property in the Lokalise web editor: `{"force-cdata": true}`.
