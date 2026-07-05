# Compose Performance Checklist

Use this reference while reviewing or optimizing Compose code.

## State

- Are state objects immutable and reasonably stable?
- Are composables reading only the state they need?
- Are expensive derived values cached only when that materially helps?

## Lists

- Do lazy items have stable keys where identity changes matter?
- Is item content lightweight and free from repeated expensive object creation?
- Are images and side effects scoped correctly for recycled list items?

## Screen Structure

- Is business or mapping work happening inside composition?
- Are effects restarting more often than intended?
- Are previews or debug-only helpers leaking into runtime code?

## Startup

- Can noncritical work move after first frame?
- Are heavyweight dependencies initialized lazily when safe?
