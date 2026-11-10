Creating an ISO8583 switch that will read a message from a fake terminal and send it to the appropriate issuer and handle the response back and route it back to the terminal.

## Decisions
- **Code layout: package-by-component, single Maven module** (`codec`, `transport`, `switching`, `sims`). Why: code that changes together lives together and the codec stays free of I/O; boundaries are not known yet, so Maven modules would be premature.
