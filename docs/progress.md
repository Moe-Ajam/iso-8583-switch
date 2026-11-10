# Progress

## Where we left off

- **Working on:** the codec (reading MTI + bitmap).
- **Step:** coach review 2026-11-09 ([review](coaching/2026-11-09-review.md)), step 1: make `Codec.readMessage` return a `Message` instead of printing.
- **Then:** step 2, first JUnit test. **2026-11-10: started the testing mini-lesson** (testing basics → JUnit → apply to codec), Done: why tests exist, arrange-act-assert, JUnit 6 added (BOM + surefire), toy `GreaterTest` passes. Now: first real test on `Codec` (`CodecTest`). Parked: IntelliJ Ctrl+R disabled (Ctrl+Shift+R works).
- **Open questions:** why `Message` has one Java field per data element (decision in the review). Mahmoud leans "walking skeleton ASAP" over "finish codec first", but hasn't given his reason yet. Record it in scope.md once he does.
- **Concepts introduced latest session:** compute-then-return; arrange-act-assert.

## Session log
