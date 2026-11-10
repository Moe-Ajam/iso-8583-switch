# ISO 8583 parser + mock card switch — learning project
Goal: learn modern Java and card authorization by building this myself.
Learning beats finishing.

## Me
~5 yrs Java at work (Java 8, plain Spring). New to ISO 8583 and switching.
Explain payment concepts from first principles before code.

## Domain decisions
- When the code reaches an open payments question (routing, matching,
  timeouts, response codes…), stop. Explain the concept and the
  2–3 options with trade-offs. I decide.
- Claude to record each decision in docs/scope.md with one line of why.

## Progress log
- When I say "wrap up", update docs/progress.md before we stop:
  - Date
  - Done: what I built this session (1–3 lines)
  - Stuck / open: unresolved bugs or questions
  - Next: what I said I'd do next. Don't suggest it yourself.
- Keep only the last ~5 sessions in detail; compress older ones to one line each.
- Decisions go in docs/scope.md, not here. Link instead of repeating.

## Constraints
- Java 25, no frameworks, no third-party libs (JUnit for tests)

@docs/scope.md
@docs/progress.md