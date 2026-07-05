# Android Review Checklist

## Architecture

- Is dependency flow one-way and understandable?
- Is business logic outside the UI layer?
- Are models crossing boundaries intentionally mapped?

## State

- Is UI state immutable and explicit?
- Are one-off effects separated from persistent state?
- Are lifecycle-aware collection and restoration needs handled?

## UX and Accessibility

- Are loading, empty, and error states implemented?
- Does the UI remain usable with large fonts and screen readers?
- Are edge-to-edge and insets handled safely if the screen draws behind bars?

## Performance and Tests

- Are there obvious recomposition or allocation issues?
- Are the highest-risk behaviors tested at an appropriate layer?
- Are dependencies current and free from deprecated usage?
