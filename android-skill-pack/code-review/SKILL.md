---
name: code-review
description: Use this skill when the user asks for an Android code review, implementation audit, readiness check, or quality pass before finishing work. Trigger it for review requests covering architecture, Compose UI quality, state management, performance, accessibility, testing, security, dependency hygiene, maintainability, and minimal-diff discipline across Kotlin and Android projects.
metadata:
  short-description: Android implementation review checklist
---

# Code Review

Use this skill for final Android quality review before considering a change complete.

## Outcomes

- Findings focus on real bugs, regressions, missing tests, architectural drift, and UX risk.
- Reviews stay grounded in behavior, not personal style.
- Completion checks are consistent across Android work.

## Review Order

1. Correctness and regressions.
2. Architecture and dependency direction.
3. State management and lifecycle safety.
4. Accessibility and user impact.
5. Performance and unnecessary complexity.
6. Testing gaps.
7. Readability, maintainability, and dependency hygiene.

## Rules

- Prioritize findings over summaries.
- Call out architecture violations such as UI owning business logic or repositories leaking transport models.
- Check Compose state ownership, recomposition risk, and side-effect placement.
- Check accessibility basics: semantics, contrast, touch targets, focus, and large text behavior.
- Verify failure paths and empty states.
- Flag deprecated APIs, stale dependency practices, and avoidable complexity.
- Prefer minimal diffs and challenge unrelated edits.

## Read More

- For a compact review rubric, read [references/review-checklist.md](references/review-checklist.md).

## Guardrails

- Do not inflate the review with cosmetic nits when correctness risks exist.
- Do not assume "works on my device" is sufficient verification.
- Do not approve code that lacks clear state ownership or safe failure behavior.
