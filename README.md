# MCQ Quiz

Android quiz assignment built with Kotlin, Jetpack Compose, and Material 3.

## Overview

This project is a single-activity quiz application that loads multiple-choice questions from the provided remote JSON source and guides the user through the complete quiz flow:

- loading state on launch
- one-question-at-a-time quiz flow
- answer reveal with correct and selected state
- auto-advance after 2 seconds
- skip support
- current streak and longest streak tracking
- streak highlight after 3 consecutive correct answers
- final results screen
- restart quiz flow

## Theme And UI Direction

For the visual design, I chose a **DBMCI-inspired white and blue theme**.

The UI direction is based on:

- clean white surfaces for content areas
- soft blue backgrounds and accents for the quiz identity
- restrained highlight colors for correct, incorrect, and streak states
- subtle motion for answer reveal, progress, and streak emphasis

The goal was to keep the interface calm, readable, and polished without changing the assignment flow.

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Retrofit
- Kotlinx Serialization
- Coroutines
- Gradle Kotlin DSL
- Version Catalog

## Architecture

The app follows **MVVM with Clean Architecture**.

Dependency flow:

`UI -> ViewModel -> UseCase -> Repository -> Data Source`

Project structure:

```text
app/src/main/java/com/example/quizassignment
├── app
├── core
│   ├── common
│   └── designsystem
├── data
│   ├── datasource
│   │   └── remote
│   ├── mapper
│   └── repository
├── di
├── domain
│   ├── model
│   ├── repository
│   └── usecase
└── feature
    └── quiz
        ├── mapper
        ├── navigation
        └── presentation
            ├── components
            └── screen
```

## Main Flow

### Loading

- App starts on the loading route.
- Questions are fetched from the remote source.
- If a session already exists and is still active, the app resumes it.

### Quiz

- One question is shown at a time.
- The user can either select an answer or skip.
- On answer selection:
  - selected answer is locked
  - correct answer is revealed
  - auto-advance starts after 2 seconds
- On skip:
  - question advances immediately
  - current streak resets

### Results

- Results are shown only after the quiz is completed.
- Summary includes:
  - correct answers
  - total questions
  - skipped questions
  - longest streak
- Restart begins a fresh session.

## State Management

- Screen state is exposed from ViewModels using `StateFlow`
- UI state is immutable
- Events flow from UI to ViewModel
- Business rules stay inside use cases
- Quiz session state is shared through a small feature-level store to keep loading, quiz, and results synchronized

## Important Business Rules

- questions must contain valid answer indices
- answer selection is idempotent per question
- skipped questions break the current streak
- longest streak is preserved across the full session
- results are available only when the session is complete

## Networking

- Questions are fetched from the provided remote gist endpoint through Retrofit
- Parsing is handled with Kotlinx Serialization
- Network failures are surfaced through the existing error flow

Current note:

- the project currently uses the remote source only
- there is no bundled offline fallback JSON in the current implementation
- there is no logging interceptor configured yet for API calls

## Accessibility And UX Notes

- important answer states are not communicated by color alone
- headings are exposed semantically where appropriate
- loading and error messaging are surfaced clearly
- motion is subtle and tied to quiz feedback
- touch interactions are built with Material components and standard accessibility behavior

## Build Commands

Build debug APK:

```bash
./gradlew :app:assembleDebug
```

Run unit tests:

```bash
./gradlew :app:testDebugUnitTest
```

Run lint:

```bash
./gradlew :app:lintDebug
```

Run instrumentation tests on a device or emulator:

```bash
./gradlew :app:connectedDebugAndroidTest
```


## Notes

- The app keeps the architecture intentionally small for assignment.
- The code avoids unnecessary abstractions while preserving clean separation of concerns.
- The focus of the implementation is correctness, maintainability, and a polished user experience.
