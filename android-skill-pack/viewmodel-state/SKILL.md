---
name: viewmodel-state
description: Use this skill when the user asks to design, refactor, or debug Android screen state, events, side effects, or ViewModel logic in Jetpack Compose apps. Trigger it for immutable UI state, state holders, one-off effects, SavedStateHandle usage, Flow and StateFlow exposure, loading and error modeling, collectAsStateWithLifecycle, and unidirectional data flow between composables and view models.
metadata:
  short-description: ViewModel state and event patterns
---

# ViewModel State

Use this skill to keep Android screen state explicit, immutable, and lifecycle-aware.

## Outcomes

- Each screen has a clear source of truth.
- UI events, persistent state, and one-time effects are separated.
- State survives lifecycle changes appropriately.

## Rules

- Expose immutable UI state from the view model.
- Represent screen state with data classes or sealed interfaces.
- Keep business state in the view model and transient visual state local unless shared control is required.
- Model user intent as explicit events or actions.
- Distinguish persistent state from one-off effects such as navigation or toasts.
- Prefer `StateFlow` for ongoing state and lifecycle-aware collection from Compose.
- Use `SavedStateHandle` for small restorable screen inputs and state that must survive process death.
- Derive display-specific values from canonical state rather than duplicating them.
- Make loading, empty, success, and failure states explicit when they affect UI behavior.

## Process

1. Define the screen contract: state, events, effects.
2. Identify the canonical state owner.
3. Reduce incoming events into new immutable state.
4. Keep side effects isolated behind clear boundaries.
5. Preview and test key states independently of the UI shell.

## Guardrails

- Do not expose mutable state directly to the UI.
- Do not mix navigation events into long-lived state objects unless the behavior is truly persistent.
- Do not let composables mutate repository or domain state directly.
- Do not hide failure paths behind nullable fields and implicit assumptions.
