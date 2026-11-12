Creating an ISO8583 switch that will read a message from a fake terminal and send it to the appropriate issuer and handle the response back and route it back to the terminal.

## Decisions
- **Code layout: package-by-component, single Maven module** (`codec`, `transport`, `switching`, `sims`). Why: code that changes together lives together and the codec stays free of I/O; boundaries are not known yet, so Maven modules would be premature.
- **Walking skeleton first, thin version** (2026-11-11): one fake issuer (no routing), one request in flight at a time (no STAN/RRN matching), codec reads only MTI + bitmap. Why: proving the whole flow fits together (terminal → switch → issuer → back) matters more now than reading specific data elements. Revisit when: adding a second issuer (needs DE2/BIN routing) or concurrent requests (needs DE11/DE37 matching).
