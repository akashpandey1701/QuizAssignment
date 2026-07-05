# AGENTS.md

# Project

Android Quiz Assignment

This project is an interview assignment.
Prioritize correctness, maintainability, clean architecture, and polished user experience over speed.

---

# Core Principles

- Think before coding.
- Prefer the smallest correct solution.
- Minimize code changes.
- Follow existing project structure.
- Never introduce unnecessary complexity.
- Production-quality code only.

---

# Workflow

For every task:

1. Understand the requirement.
2. Identify affected files.
3. Create a short implementation plan.
4. Implement the smallest possible change.
5. Verify the result.
6. Summarize changes.

Do not start coding before understanding the complete task.

---

# Scope Control

Only modify files directly related to the requested feature.

Do not:

- perform unrelated refactoring
- rename files without reason
- rewrite working code
- change project structure unless required

Prefer minimal diffs.

---

# Architecture

Use MVVM with Clean Architecture.

Dependencies must always flow:

UI
↓
ViewModel
↓
UseCase
↓
Repository
↓
Data Source

Rules:

- UI contains no business logic.
- ViewModel owns screen state.
- UseCases contain business rules.
- Repository handles data coordination.
- Data layer handles parsing and persistence.

---

# UI

Use Jetpack Compose.

UI should be:

# UI

Build the UI using Jetpack Compose and Material 3.

Follow unidirectional data flow (UDF):

UI → Event → ViewModel → State → UI

Guidelines:

- Keep business state in the ViewModel.
- Keep transient UI state (e.g. focus, scroll position, dialog visibility, animations) within composables when appropriate.
- Hoist state to the lowest common parent only when it needs to be shared or controlled externally.
- Design reusable UI components to be stateless whenever practical, exposing state and callbacks instead of managing business logic internally.
- Keep composables small, focused, and reusable with a single responsibility.
- Separate presentation from business logic.
- 
# State

State must be immutable.

Represent screen state with data classes or sealed interfaces.

Never mutate UI directly.

Events should always flow:

UI → ViewModel → State → UI

---

# Assignment Requirements

The following behavior is mandatory:

- Load quiz data
- Show loading state
- Display questions
- Show four options
- Reveal selected answer
- Reveal correct answer
- Advance automatically after 2 seconds
- Skip questions
- Track current streak
- Track longest streak
- Highlight streak after three consecutive correct answers
- Show results screen
- Restart quiz

---

# UX

Animations should improve usability.

Prefer subtle:

- Fade
- Slide
- Scale
- Progress
- Badge transitions

Avoid distracting animations.

Use Material 3.

Respect accessibility guidelines.

---

# Code Quality

Write readable code.

Prefer:

- small functions
- meaningful names
- immutable models
- Kotlin idioms
- early returns

Avoid:

- !!
- duplicated logic
- magic numbers
- deeply nested code

---

# Performance

Avoid unnecessary recomposition.

Remember expensive objects.

Only optimize when there is measurable benefit.

---

# Error Handling

Handle failures gracefully.

Never assume:

- valid JSON
- non-empty question list
- valid answers

Fail safely.

---

# Verification

Before considering any task complete:

- Project builds successfully
- No compiler warnings
- No unused imports
- No debug code
- No TODOs
- No broken navigation

---

# Definition of Done

A task is complete only if:

- Requirement is fully implemented
- Architecture is respected
- Code is readable
- UI is polished
- Accessibility is maintained
- Project builds successfully

---

# Response Format

For every implementation provide:

## Plan

## Files Modified

## Summary of Changes

## Verification

Keep explanations concise.