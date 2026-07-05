---
name: adaptive-layout
description: Use this skill when the user asks to make an Android app responsive or adaptive across phones, small phones, tablets, foldables, ChromeOS, desktop windowing, landscape, or resizable windows. Trigger it for window size classes, list-detail layouts, pane layouts, responsive spacing, posture-aware UI, multi-window support, and Compose layouts that must scale across compact, medium, and expanded widths without hardcoded dimensions.
metadata:
  short-description: Adaptive layouts for every Android form factor
---

# Adaptive Layout

Use this skill to make Compose UIs work well across changing window sizes and device classes.

## Outcomes

- Screens adapt to compact, medium, and expanded windows.
- Layout decisions are based on available space, not device assumptions.
- The same feature remains usable on phones, tablets, foldables, and ChromeOS.

## Rules

- Base adaptive decisions on window size classes and current constraints.
- Prefer canonical patterns such as list-detail, supporting pane, and adaptive navigation.
- Use responsive spacing and max-width strategies instead of fixed large widths.
- Keep content readable in portrait, landscape, split-screen, and freeform windowing.
- Consider foldables and hinges when placing persistent panes or critical actions.
- Make expanded layouts additive, not separate products.
- Test empty, loading, dense, and error states at multiple widths.

## Process

1. Define the compact layout first.
2. Identify where extra width adds utility: secondary pane, richer navigation, wider reading measure.
3. Introduce medium and expanded adaptations with shared content models.
4. Keep actions reachable and readable when windows shrink again.
5. Verify resizing behavior instead of assuming static breakpoints.

## Read More

- For layout patterns and breakpoint behaviors, read [references/layout-patterns.md](references/layout-patterns.md).

## Guardrails

- Do not branch on device names or tablet booleans.
- Do not hardcode widths for cards, sheets, or panes unless capped by readability rules.
- Do not create a separate code path for every form factor when one adaptive layout can serve them.
