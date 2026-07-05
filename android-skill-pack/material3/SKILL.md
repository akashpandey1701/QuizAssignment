---
name: material3
description: Use this skill when the user asks for Material 3 theming, color systems, typography, shape tokens, design-system setup, premium Compose styling, or visual refinement of Android screens. Trigger it for requests about light and dark themes, dynamic color strategy, design tokens, component styling, visual hierarchy, branded polish, and creating elegant Android UIs that respect Material guidance without looking generic or AI generated.
metadata:
  short-description: Material 3 theming and premium polish
---

# Material 3

Use this skill to build Android interfaces that feel refined, coherent, and production-ready.

## Outcomes

- Material 3 tokens are applied deliberately rather than mechanically.
- Themes support accessibility, contrast, and maintainable styling.
- Screens feel premium and brand-aware without fighting platform conventions.

## Apply This Skill When

- Setting up or refactoring app theme layers.
- Choosing color, typography, spacing, and component emphasis.
- Upgrading a generic-looking UI into a polished Material 3 experience.
- Reviewing whether a screen follows Material 3 while keeping a distinctive identity.

## Rules

- Use Material 3 as the baseline system for colors, typography, shapes, and components.
- Prefer theme tokens and semantic styling over hardcoded colors and sizes.
- Support dynamic color when it matches the product and provide a stable fallback palette.
- Build contrast and hierarchy through tone, scale, spacing, and surface layering.
- Keep accent usage intentional; one strong visual idea is better than many weak ones.
- Style components through the theme before introducing local overrides.
- Respect large text, contrast needs, and touch target requirements.
- Match motion and elevation to importance; avoid decorative noise.

## Process

1. Establish brand direction and the core surface/accent relationship.
2. Define color, type, and shape tokens that scale across screens.
3. Apply tokens to common shells first: top bars, navigation, cards, forms, buttons.
4. Tighten spacing and emphasis until the layout reads clearly at a glance.
5. Recheck with accessibility settings enabled.

## Read More

- For premium visual direction and anti-patterns, read [references/premium-ui-principles.md](references/premium-ui-principles.md).

## Guardrails

- Do not hardcode ad hoc colors throughout feature code.
- Do not equate premium with heavy gradients, oversized shadows, or dense ornament.
- Do not treat every section as equally important.
