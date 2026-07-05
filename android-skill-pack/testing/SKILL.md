---
name: testing
description: Use this skill when the user asks to add, improve, review, or debug tests in an Android project. Trigger it for unit tests, ViewModel tests, repository tests, fake data sources, coroutine testing, Flow testing, Compose UI tests, navigation tests, error-path coverage, and Android code that needs reliable regression protection without excessive test fragility.
metadata:
  short-description: Android testing across layers
---

# Testing

Use this skill to build a pragmatic Android test suite with fast feedback and meaningful coverage.

## Outcomes

- Core behavior is covered at the right layer.
- Tests validate edge cases and failure paths, not only happy paths.
- UI tests are used deliberately where unit tests are not enough.

## Rules

- Test business logic close to the use case or view model that owns it.
- Prefer fakes and deterministic test doubles over brittle integration-heavy setups.
- Use coroutine test utilities for asynchronous logic.
- Test Flow emissions, loading states, retries, and failures explicitly.
- Add Compose UI tests when interaction, semantics, or navigation behavior matters.
- Keep test names descriptive of the behavior under test.
- Cover empty input, malformed data, cancellation, and partial failure when relevant.

## Process

1. Identify the highest-value behavior and the cheapest reliable layer to test it.
2. Write or update fakes before over-mocking.
3. Cover success, failure, and boundary cases.
4. Run the narrowest useful test scope first, then broaden if needed.

## Read More

- For layer-by-layer expectations, read [references/android-test-matrix.md](references/android-test-matrix.md).

## Guardrails

- Do not default to UI tests for business rules that unit tests can cover faster.
- Do not assert incidental implementation detail when observable behavior is enough.
- Do not skip error-path tests for parsing, persistence, or network-backed flows.
