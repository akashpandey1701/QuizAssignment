# Versioning Policy

Use this reference when editing Android build dependencies.

## Upgrade Order

1. Confirm the requested scope.
2. Check official release notes for the latest stable version of the relevant tool or library.
3. Align foundational tooling first when needed: Gradle wrapper, Android Gradle Plugin, Kotlin.
4. Align library families next: Compose BOM and Compose artifacts, Lifecycle, Navigation, Room, Hilt.
5. Rebuild and run targeted tests after each logical upgrade group.

## Guardrails

- Prefer one source of truth for versions.
- Avoid alpha, beta, rc, snapshot, and `+` versions unless the user explicitly asks for preview software.
- Keep migration notes close to the code change when an upgrade forces behavior changes.
- If two libraries conflict, prefer the compatibility guidance from their official documentation over ad hoc overrides.
