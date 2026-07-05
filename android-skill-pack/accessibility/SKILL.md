---
name: accessibility
description: Use this skill when the user asks to design, implement, audit, or fix Android accessibility in Jetpack Compose or Material 3 screens. Trigger it for TalkBack support, semantics, content descriptions, touch targets, color contrast, large fonts, dynamic text, focus order, keyboard navigation, reduced motion, accessible forms, and inclusive Android UI behavior across phones, tablets, and other form factors.
metadata:
  short-description: Accessibility for Compose and Material 3
---

# Accessibility

Use this skill to make Android interfaces usable for more people by default.

## Outcomes

- Compose screens work with screen readers, large text, and non-touch input.
- Important actions remain perceivable, operable, and understandable.
- Accessibility is built into the design rather than patched later.

## Rules

- Ensure semantics describe purpose, state, and result.
- Provide content descriptions only when visuals are meaningful and not already conveyed by text.
- Maintain at least 48dp touch targets for interactive elements.
- Verify color contrast and do not rely on color alone for meaning.
- Support large fonts and dynamic text without clipping or overlap.
- Preserve logical focus order for touch exploration, keyboard, and D-pad use.
- Keep motion optional or restrained when it could disorient users.
- Make error states and validation messages explicit and understandable.

## Process

1. Identify the interactive and informative elements on the screen.
2. Add or refine semantics where meaning is missing.
3. Check large-font and high-contrast behavior.
4. Verify focus movement and keyboard reachability when relevant.
5. Re-test after layout or animation changes.

## Read More

- For a Compose-focused checklist, read [references/compose-accessibility-checklist.md](references/compose-accessibility-checklist.md).

## Guardrails

- Do not add redundant content descriptions that duplicate visible text.
- Do not hide critical information only in placeholders, color, or motion.
- Do not shrink touch targets to satisfy dense layouts.
