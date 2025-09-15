# Build Logic – Plugins – Categories

The **CategoriesPlugin** automates the process of managing category data for the app.
It handles the following steps:

- Downloads the latest JSON files published as artifacts from the web repository.
- Moves these files into the correct Android module so they can be bundled with the app.
- Ensures the app always builds with up-to-date category definitions.

This removes manual steps and keeps category data in sync between the web and mobile projects.
