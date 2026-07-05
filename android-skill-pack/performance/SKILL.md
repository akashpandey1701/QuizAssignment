---
name: performance
description: Use this skill when the user asks to optimize, profile, review, or debug Android app performance, especially Jetpack Compose rendering, recomposition, scrolling, memory use, and startup behavior. Trigger it for unnecessary recomposition, unstable state, allocation churn, lazy list performance, image loading cost, startup work, baseline profiling, and pragmatic Android performance improvements backed by measurable impact.
metadata:
  short-description: Android and Compose performance tuning
---

# Performance

Use this skill to improve Android runtime behavior without premature over-engineering.

## Outcomes

- Compose work is driven by stable state and scoped recomposition.
- Startup, scrolling, and rendering stay smooth.
- Performance changes are based on actual bottlenecks.

## Rules

- Measure before applying nontrivial optimizations.
- Prefer stable immutable UI models and scoped state reads.
- Use `remember` only for expensive objects or values that must survive recomposition.
- Use `derivedStateOf` when it reduces repeated expensive derivation or limits downstream recomposition.
- Provide stable item keys in lazy collections when identity matters.
- Keep heavy mapping, sorting, and filtering out of composables.
- Avoid repeated allocations in hot paths such as list items and animation frames.
- Load images at the correct size and lifecycle.
- Consider baseline profiles and startup deferral when launch cost is material.

## Process

1. Identify the symptom: jank, slow startup, high memory, dropped frames, or redundant work.
2. Locate the hot path before editing architecture.
3. Remove obvious instability, repeated work, or excessive allocations.
4. Re-measure and stop when the bottleneck is resolved.

## Read More

- For a focused Compose checklist, read [references/compose-performance-checklist.md](references/compose-performance-checklist.md).

## Guardrails

- Do not add `remember` or `derivedStateOf` everywhere by default.
- Do not optimize readability away for theoretical gains.
- Do not perform blocking IO, JSON parsing, or large transformations in composables.
