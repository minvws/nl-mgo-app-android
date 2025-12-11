# Build Logic – Plugins – Health Data Config Plugin

The **HealthDataConfigPlugin** automates the process of managing health data configuration for the app.
It handles the following steps:

- Downloads the latest JSON files published as artifacts from the web repository.
- Moves these files into the correct Android module so they can be bundled with the app.

This removes manual steps and keeps health data configuration in sync between the web and mobile projects.
