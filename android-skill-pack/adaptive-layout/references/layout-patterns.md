# Adaptive Layout Patterns

Use this reference when choosing how a feature should scale across widths.

## Compact

- Favor a single primary column.
- Keep navigation simple and bottom-reachable when appropriate.
- Avoid side-by-side panes unless the content is still readable.

## Medium

- Consider supporting panes for filters, summaries, or secondary context.
- Increase spacing carefully; wider does not mean emptier.
- Promote persistent navigation only when it improves task flow.

## Expanded

- Use list-detail or multi-pane layouts when simultaneous context is valuable.
- Cap reading width for text-heavy content.
- Keep hierarchy clear so the screen does not become a wall of containers.

## Resizing Checklist

- Ensure content reflows without overlap or clipped actions.
- Keep scroll behavior predictable when panes appear or disappear.
- Preserve selection and navigation state across width changes.
- Re-evaluate dialog, sheet, and menu sizes in expanded windows.
