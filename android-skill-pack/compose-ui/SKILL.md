---
name: compose-ui
description: Use this skill when the user asks to build, redesign, refactor, or debug Jetpack Compose screens, components, forms, lists, app bars, scaffolds, or design systems. Trigger it for requests about composable structure, state hoisting, unidirectional data flow, previews, reusable components, modifiers, lazy layouts, screen polish, and modern Android UI implementation with Material 3 and Compose-first patterns.
metadata:
  short-description: Build polished UI with Jetpack Compose
---

# Compose UI

Use this skill for production-grade Compose screens and reusable components.

## Outcomes

- Compose screens are small, readable, and driven by state.
- Components are reusable, previewable, and easy to test.
- Layouts feel intentional rather than generic.

## Apply This Skill When

- Building a new screen or feature in Jetpack Compose.
- Refactoring large composables into focused pieces.
- Improving UI structure, previews, or state hoisting.
- Fixing issues caused by modifier order, unstable parameters, or duplicated layout code.

## Rules

- Model screen behavior with unidirectional data flow: UI -> event -> state holder -> UI.
- Hoist business state to the view model; keep ephemeral UI-only state local when safe.
- Pass data and callbacks instead of giving composables hidden responsibilities.
- Prefer stateless reusable components with focused parameters.
- Keep composables small enough that a reader can understand them quickly.
- Use `collectAsStateWithLifecycle()` for observable UI state from a view model.
- Use `rememberSaveable` for user-entered or navigation-restorable UI state.
- Favor `LazyColumn`, `LazyRow`, grids, and paging-friendly patterns for dynamic collections.
- Use previews for component states and edge cases.
- Treat modifier order as behavior, not decoration.

## Screen Construction

1. Define the `UiState` and user events first.
2. Build the screen shell with `Scaffold` only when it adds value.
3. Split content into semantic sections and focused child composables.
4. Extract reusable controls only after the usage pattern is clear.
5. Preview loading, empty, error, dense-content, and large-font states.

## Premium UI Direction

- Use spacing, typography, hierarchy, and restraint before adding visual effects.
- Avoid default-looking layouts with evenly weighted boxes everywhere.
- Use strong alignment, deliberate whitespace, and clear emphasis.
- Prefer a few memorable surfaces or accents over busy decoration.

## Guardrails

- Do not put navigation or repository calls inside leaf composables.
- Do not create giant parameter lists when a small state model is clearer.
- Do not overuse `CompositionLocal`; reserve it for true ambient dependencies.
- Avoid unnecessary wrappers and nested `Box` stacks when simpler layout primitives work.
