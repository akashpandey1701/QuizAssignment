---
name: android-clean-architecture
description: Use this skill when the user asks to design, scaffold, refactor, review, or debug Android app architecture using MVVM, Clean Architecture, repositories, use cases, dependency injection, feature modules, or package structure. Trigger this skill for requests about presentation/domain/data layering, dependency direction, business rules placement, state ownership boundaries, repository contracts, error handling flow, and maintainable Android code organization across Jetpack Compose projects.
metadata:
  short-description: Android MVVM and Clean Architecture
---

# Android Clean Architecture

Use this skill to keep Android codebases layered, testable, and easy to change.

## Outcomes

- Organize code into presentation, domain, and data concerns with one-way dependencies.
- Keep business rules in use cases, coordination in repositories, and platform details in data sources.
- Make features easy to test, review, and evolve without broad rewrites.

## Apply This Skill When

- Creating a new Android project structure.
- Refactoring tightly coupled screens, view models, repositories, or data sources.
- Adding a new feature and deciding where models, mappers, and rules belong.
- Reviewing whether UI logic, business logic, and IO concerns are separated correctly.

## Rules

- Follow dependency flow: UI -> ViewModel -> UseCase -> Repository -> Data Source.
- Keep Compose UI free of business rules and persistence logic.
- Give each screen a focused `UiState` plus explicit user events.
- Put business decisions, validation, transformations, and orchestration in use cases.
- Keep repositories as interfaces in the domain or core boundary and implementations in data.
- Use data sources for network, database, cache, file, and platform IO details only.
- Map remote and local models to stable domain models before they reach the UI.
- Prefer constructor injection and small interfaces over service locators or global state.
- Split by feature first when the app is large enough to justify it.
- Fail safely: model loading, empty, success, and error states explicitly.

## Workflow

1. Identify the feature entry points, external dependencies, and state owners.
2. Define or confirm the domain model and use case boundary.
3. Shape repository contracts around use cases, not around transport formats.
4. Keep mapping at the data boundary.
5. Expose immutable screen state from the view model.
6. Add tests at the use case and view model layers before expanding scope.

## Guardrails

- Do not let composables call repositories directly.
- Do not pass Retrofit DTOs, Room entities, or database cursors into UI state.
- Do not merge unrelated responsibilities into a single view model or repository.
- Do not create use cases for trivial pass-through behavior unless that boundary adds clarity.
- Prefer the smallest structure that preserves clean dependency direction.

## Delivery Standard

- New behavior fits the existing architecture or improves it with a minimal diff.
- Public seams are named around intent, not implementation detail.
- State and side effects are explicit.
- The resulting code is straightforward to test.
