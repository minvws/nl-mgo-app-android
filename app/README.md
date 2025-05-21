# App

This module contains all classes related to the entry point of the app. It connects all feature modules and provides navigation to structure and display the MGO app properly.

## Notable files

- **MainApplication**: The application class.
- **MainActivity**: The launcher activity. Note that this app follows a single-activity
  architecture, meaning this should be the only activity in the project (excluding any libraries
  that use activities or intents). The main purpose of this activity is to set up Compose with the
  correct theming and provide navigation to all the different feature modules.
- **build.gradle.kts**: The Gradle file that specifies all app-related configurations, such as
  versioning, flavors, and release settings.
