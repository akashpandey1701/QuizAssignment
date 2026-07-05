# Android Test Matrix

Use this reference to choose the right test layer.

## Use Cases

- Validate business rules, branching, and coordination.
- Prefer plain JVM tests when Android framework access is unnecessary.

## ViewModels

- Validate state reduction, events, and one-off effects.
- Assert loading, success, empty, and failure transitions.

## Repositories

- Validate mapping, cache behavior, and data-source coordination.
- Use fakes or controlled test implementations for upstream dependencies.

## Compose UI

- Validate semantics, visible state, user interaction, and navigation wiring.
- Use them when layout or accessibility behavior is part of the requirement.
