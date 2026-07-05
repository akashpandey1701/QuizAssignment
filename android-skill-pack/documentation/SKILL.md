---
name: documentation
description: Use this skill when the user asks to document Android code, generate or improve README sections, add KDoc, explain architecture, or keep implementation notes aligned with code changes. Trigger it for public API documentation, setup instructions, feature summaries, testing notes, architecture overviews, migration notes, and concise technical writing for Kotlin and Android projects.
metadata:
  short-description: Concise Android code and project docs
---

# Documentation

Use this skill to keep Android documentation accurate, concise, and worth reading.

## Outcomes

- Public APIs and important architecture decisions are documented clearly.
- README sections help future contributors without repeating the code.
- Documentation changes stay scoped to behavior that actually changed.

## Rules

- Document intent, contracts, and non-obvious behavior rather than restating code.
- Add KDoc to public APIs, extension points, and tricky abstractions.
- Keep README content task-oriented: what the app does, how to run it, how it is structured, how to test it.
- Update docs when setup, architecture, or observable behavior changes.
- Prefer short concrete examples over long prose.
- Remove stale comments instead of piling on new ones.

## Process

1. Identify which audience needs the documentation: maintainers, integrators, or reviewers.
2. Document only the contracts and decisions that code alone does not make obvious.
3. Keep examples aligned with the current API and architecture.
4. Re-read for duplication and delete anything that adds noise.

## Read More

- For lightweight README guidance, read [references/readme-sections.md](references/readme-sections.md).

## Guardrails

- Do not write comments for trivial assignments or obvious control flow.
- Do not create lengthy docs that drift from the codebase quickly.
- Do not document private churn unless it affects maintainers materially.
