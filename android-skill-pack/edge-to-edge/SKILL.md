---
name: edge-to-edge
description: Use this skill when the user asks to enable or fix Android edge-to-edge UI, system bars, navigation bars, status bars, IME handling, safe drawing, or window inset behavior in Jetpack Compose. Trigger it for requests about immersive layouts, Scaffold padding, inset-aware modifiers, keyboard overlap, translucent bars, icon contrast, and full-screen Android experiences that must remain usable and accessible.
metadata:
  short-description: Edge-to-edge and window inset handling
---

# Edge to Edge

Use this skill to make Android screens draw behind system bars safely and intentionally.

## Outcomes

- Edge-to-edge is enabled consistently.
- Content respects status bars, navigation bars, cutouts, and IME movement.
- System bar appearance matches the current surface and accessibility needs.

## Rules

- Enable edge-to-edge at the activity level.
- Treat window insets as layout input, not as an afterthought.
- Apply inset-aware padding at the correct container level.
- Handle IME movement explicitly for focused input flows.
- Keep tappable controls out of obstructed regions.
- Ensure system bar icon contrast stays correct for the current background.
- Recheck dialogs, sheets, snackbars, and bottom bars under gesture navigation.

## Process

1. Confirm the activity and theme allow edge-to-edge drawing.
2. Identify which container owns top, bottom, and IME inset handling.
3. Apply Compose inset modifiers where content actually needs them.
4. Validate collapsed, scrolling, and keyboard-open states.
5. Verify both gesture and three-button navigation when possible.

## Guardrails

- Do not stack multiple inset paddings on the same axis without intent.
- Do not rely on hardcoded status-bar heights or navigation-bar guesses.
- Do not let the keyboard cover focused fields or confirmation actions.
