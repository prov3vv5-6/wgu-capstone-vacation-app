# WGU Capstone Vacation Planner

An Android vacation planning application with secure user authentication, persistent SQLite storage, and search/reporting features. Built as the capstone project for the WGU Bachelor of Science in Software Engineering (course D424).

## Overview

The app lets users track planned vacations and the excursions associated with each, with full create, read, update, and delete operations on both. Each vacation can have multiple excursions tied to it through a foreign-key relationship in the underlying database. Users authenticate against a locally stored credential (hashed with SHA-256) before accessing their data and can generate filtered reports of their vacation history.

## Tech Stack

- **Language:** Java
- **Platform:** Android (Android Studio, Gradle)
- **Database:** SQLite via the Room persistence library
- **UI:** Activities, RecyclerView with custom adapters, XML layouts
- **Architecture:** Repository pattern over DAO interfaces, with DAO-backed entities

## Features

- Secure login with SHA-256 password hashing and input validation
- Full CRUD on vacations — title, hotel, start/end dates, and client name
- Full CRUD on excursions tied to a parent vacation via foreign key
- Search and report generation with multi-row results, timestamps, and detailed display
- Save and update confirmation messages for clear user feedback
- Notification/reminder system via BroadcastReceiver
- Unit tests covering login validation logic

## Architecture

The codebase is organized into clear layers:

- **`UI/`** — Activities, Adapters, and the BroadcastReceiver handling user-facing logic
- **`dao/`** — DAO interfaces defining database operations for Vacations and Excursions
- **`database/`** — Room database class and a Repository class abstracting data access
- **`entities/`** — Plain Java data classes representing Vacation and Excursion records

Database operations run off the main thread via Room's executor service.

## Building and Running

1. Clone the repository.
2. Open the project in Android Studio (Hedgehog or newer recommended).
3. Allow Gradle to sync and resolve dependencies.
4. Run on an emulator or physical device targeting API level 24 or higher.

## Testing

Unit tests covering login validation logic live in `app/src/test/`. Instrumented tests live in `app/src/androidTest/`.

## Skills Demonstrated

- Android application development with the Java SDK and Gradle build system
- Persistence with Room and SQLite, including DAO design and foreign-key relationships
- Secure credential handling with SHA-256 hashing rather than plaintext storage
- Repository-pattern data access abstracting the UI from the database
- RecyclerView and adapter implementation for dynamic list display
- BroadcastReceiver and notification scheduling for time-based reminders
- Unit testing in JUnit

## Notes

This project was completed as academic work for the Western Governors University Software Engineering capstone. Code, comments, and commit history reflect the iterative development process across the course.