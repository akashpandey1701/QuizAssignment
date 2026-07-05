---
name: dependency-management
description: Use this skill when the user asks to add, upgrade, audit, align, or troubleshoot Android project dependencies, Gradle versions, version catalogs, Compose BOM usage, plugin configuration, or library selection. Trigger it for requests about Kotlin, Android Gradle Plugin, Compose BOM, Material 3, Navigation Compose, Hilt, Coroutines, Lifecycle, Room, Retrofit, Coil, serialization libraries, and keeping Android dependencies current, compatible, and stable without deprecated APIs.
metadata:
  short-description: Stable Android dependency strategy
---

# Dependency Management

Use this skill to keep Android builds modern, stable, and easy to upgrade.

## Outcomes

- Versions are centralized and aligned.
- Libraries are chosen intentionally and kept on supported stable releases.
- Build logic stays understandable as dependencies grow.

## Rules

- Verify current stable versions from official release notes or docs before pinning or upgrading.
- Prefer version catalogs for libraries and plugins.
- Use platform BOMs where they are the recommended stable mechanism, including Compose BOM.
- Keep related AndroidX, Compose, Kotlin, and Gradle pieces compatible as a set.
- Avoid snapshots, dynamic versions, and accidental transitive drift.
- Prefer stable maintained libraries that fit Android architecture and testing practices.
- Remove dead dependencies instead of carrying them forward.
- Prefer KSP over kapt when the chosen stable libraries support it cleanly.
- Isolate build logic changes to the smallest affected modules.

## Library Selection Defaults

- Kotlin-first AndroidX APIs.
- Material 3 for Compose UI.
- Lifecycle-aware state collection.
- Coroutines and Flow for async work.
- Room for structured local persistence when SQL storage is needed.
- Retrofit plus a supported serialization stack when HTTP APIs are needed.
- Coil for Compose image loading when image fetching is required.

## Process

1. Confirm whether the task is add, upgrade, align, or prune.
2. Check official stable versions before editing build files.
3. Update the version catalog or shared dependency source first.
4. Align related plugins and runtime libraries together.
5. Build and run focused tests after each upgrade set.

## Read More

- For versioning policy and upgrade guardrails, read [references/versioning-policy.md](references/versioning-policy.md).

## Guardrails

- Do not hardcode the same version in multiple modules.
- Do not upgrade unrelated libraries together without a reason.
- Do not introduce deprecated APIs just because older samples use them.
