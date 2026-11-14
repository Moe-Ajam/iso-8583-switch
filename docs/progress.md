# Progress

## Where we left off

- **Working on:** nothing open. [Brief 01](briefs/01-terminal-to-switch.md) done 2026-11-14: terminal sim sends a framed 0100 over TCP, switch prints `MTI: 0100`.
- **Step:** next brief not chosen yet. Carry-over list is at the end of Brief 01's *Check-in*.
- **History:** coach review 2026-11-09 ([review](coaching/2026-11-09-review.md)) steps 1–2 done (codec returns `Message`, first JUnit test). **2026-11-10: started the testing mini-lesson** (testing basics → JUnit → apply to codec), Done: why tests exist, arrange-act-assert, JUnit 6 added (BOM + surefire), toy `GreaterTest` passes. 2026-11-11: `CodecTest` done (readsMti, readsBitmap, clean AAA). First commit pushed to GitHub. Decided **thin walking skeleton** (see scope.md). Introduced TDD (red-green-refactor). Parked: IntelliJ Ctrl+R and "Create Test" not working.
- **2026-11-12: sockets mini-lesson** (path: why → mental model → echo example → toy echo server/client in java-practice → reading exactly N bytes → apply: terminal sends framed 0100). Did steps 1–3 (why TCP, server/client model, echo example + predictions). Mahmoud chose to skip the toy exercise and build in the switch directly. Now: first transport slice in the project. Flow agreed: issuer builds 0110 + DE39=00; switch passes through; framing = 4 ASCII digit length header, lives in `transport`.
- **2026-11-13 (from git):** `MessageReader.readMessage` done (4-digit length header, `readNBytes`) with a passing test, committed. Started `Switch` (only a `ServerSocket` import so far, uncommitted). Next unwritten piece: the switch accepting a terminal connection.
- **Open questions:** why `Message` has one Java field per data element (decision in the review). What the skeleton's transport/framing looks like (not started).
- **Concepts introduced latest session:** compute-then-return; arrange-act-assert.

## Session log
