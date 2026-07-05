---
name: animations
description: Use this skill when the user asks to add, refine, or review Jetpack Compose animations, motion design, screen transitions, progress feedback, or micro-interactions in an Android app. Trigger it for AnimatedVisibility, AnimatedContent, Crossfade, animate*AsState, spring animations, shared transitions, loading motion, gesture feedback, and tasteful motion that improves comprehension without distracting users.
metadata:
  short-description: Tasteful Compose motion and transitions
---

# Animations

Use this skill to add motion that clarifies state changes and improves feel.

## Outcomes

- Motion supports comprehension and delight without slowing the interface down.
- Animations are consistent with hierarchy, feedback, and accessibility needs.
- Transition code stays maintainable.

## Rules

- Animate meaning, not everything.
- Prefer subtle motion for visibility changes, content swaps, and emphasis.
- Match animation strength to importance and frequency.
- Use spring or eased timing that feels responsive rather than ornamental.
- Keep loading and success feedback calm and short.
- Respect reduced-motion and accessibility concerns when motion could distract or disorient.
- Favor state-driven animation APIs over imperative choreography.

## Preferred Tools

- `AnimatedVisibility`
- `AnimatedContent`
- `Crossfade`
- `animate*AsState`
- `updateTransition`
- Shared transitions when they are stable and justified in the project

## Process

1. Identify the exact state change the user should perceive.
2. Choose the smallest animation that reinforces it.
3. Verify timing across repeated interactions.
4. Recheck for jank, layout jumps, and accessibility impact.

## Guardrails

- Do not animate every surface on first render.
- Do not combine multiple strong motion effects for the same change.
- Do not let animations delay critical actions or block content readability.
