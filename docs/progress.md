# Progress

## Where we left off

- **Working on:** the codec (reading MTI + bitmap).
- **Step:** coach review 2026-11-09 ([review](coaching/2026-11-09-review.md)), step 1: make `Codec.readMessage` return a `Message` instead of printing.
- **Then:** step 2, first JUnit test. **2026-11-10: started the testing mini-lesson** (testing basics → JUnit → apply to codec), Done: why tests exist, arrange-act-assert, JUnit 6 added (BOM + surefire), toy `GreaterTest` passes. 2026-11-11: `CodecTest` done (readsMti, readsBitmap, clean AAA). First commit pushed to GitHub. Decided **thin walking skeleton** (see scope.md). Introduced TDD (red-green-refactor). Parked: IntelliJ Ctrl+R and "Create Test" not working.
- **2026-11-12: sockets mini-lesson** (path: why → mental model → echo example → toy echo server/client in java-practice → reading exactly N bytes → apply: terminal sends framed 0100). Did steps 1–3 (why TCP, server/client model, echo example + predictions). Mahmoud chose to skip the toy exercise and build in the switch directly. Now: first transport slice in the project. Flow agreed: issuer builds 0110 + DE39=00; switch passes through; framing = 4 ASCII digit length header, lives in `transport`.
- **Open questions:** why `Message` has one Java field per data element (decision in the review). What the skeleton's transport/framing looks like (not started).
- **Concepts introduced latest session:** compute-then-return; arrange-act-assert.

## Session log
