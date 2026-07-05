---
name: navigation-compose
description: Use this skill when the user asks to add, refactor, debug, or review navigation in a Jetpack Compose Android app. Trigger it for navigation graphs, routes, nested graphs, bottom navigation, deep links, argument passing, back-stack behavior, multiple back stacks, state restoration, and screen transitions implemented with Navigation Compose and Compose-first architecture.
metadata:
  short-description: Navigation patterns for Compose apps
---

# Navigation Compose

Use this skill to keep Compose navigation predictable, testable, and aligned with screen state ownership.

## Outcomes

- Destinations are modeled clearly and navigation logic stays out of leaf UI.
- Back-stack behavior is intentional.
- Arguments and results are passed safely without leaking screen internals.

## Rules

- Keep navigation decisions in screen-level handlers, coordinators, or view models as appropriate.
- Prefer route models or centralized route definitions over scattered string literals.
- Pass stable identifiers or small arguments, not large objects or mutable state.
- Restore state intentionally for tab and multi-stack flows.
- Model deep links and nested graphs around feature boundaries.
- Treat navigation as UI flow, not business logic.
- Keep each destination responsible for its own state holder.

## Process

1. Define the destination list and entry arguments.
2. Group related routes into feature-focused graphs.
3. Handle one-time navigation effects explicitly.
4. Verify back behavior, up behavior, and process recreation paths.
5. Test state restoration for tabs, detail screens, and deep links.

## Guardrails

- Do not navigate directly from low-level reusable composables.
- Do not pass repository objects, entities, or full screen models through routes.
- Do not spread route constants and argument keys across unrelated files.
