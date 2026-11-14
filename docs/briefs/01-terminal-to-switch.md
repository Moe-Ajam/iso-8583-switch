# Brief 01: Terminal sends a 0100 to the switch
Status: Done 2026-11-14 (started 2026-11-14)

## Start here

**Goal:** a fake terminal connects to the switch over TCP and sends a framed 0100; the switch
reads it, removes the framing, decodes it and prints the MTI. Two separate programs talking.

**Steps** (each: build it → write down what you expect → run → commit)
- [x] 1. Switch listens on a port and waits for a terminal. Needs: [Sockets](#server-and-client-sockets), [Closing](#closing-what-you-open-try-with-resources)
- [x] 2. Terminal connects to the switch. Needs: [Sockets](#server-and-client-sockets)
- [x] 3a. Message writer frames a message (length header in front), test first, no network. Needs: [Writing bytes](#writing-bytes-outputstream)
- [x] 3b. Terminal uses the writer to send the 0100 to the switch.
- [x] 4. Switch reads the message and removes the framing (your `MessageReader.readMessage`).
- [x] 5. Switch decodes the message and prints the MTI (your `Codec.readMessage`).

**Done when**
- [x] Your writer test passes, and `mvn test` is all green (old `CodecTest` and `MessageFramerTest` too)
- [x] Run the switch: it prints that it's listening and keeps waiting (doesn't exit)
- [x] Then run the terminal: the switch's console shows the line `0100`, and both programs end
- [x] You wrote your picks under *Decisions you'll face*, and explained the tap-a-card flow back in chat

Stuck? Open the hints for your step at the bottom, or ask me any time.

---

# Reference (open what a step needs)

## Changes to your plan
- Step 1: "opens the connection" became "listens and waits" because the side that dials in (the terminal) is the one that opens the connection; the switch only waits at a port.
- Step 3: split into 3a and 3b because if the switch prints nothing you need to know whether the writer or the socket broke. The writer gets an in-memory test first (TDD: red → green), so the network sits on top of a piece you trust.
- Step 5: added so the real run has something to show (`0100`), proving the bytes arrived intact.
- Scope: the switch handles **one** terminal, **one** message, then ends. That matches the thin walking skeleton in `scope.md`. Loops and many terminals come later.

## What you need to know

### Server and client sockets
A **server socket** is a door the switch opens on a port number and waits at; `accept()` stops
the program until someone knocks, then returns a `Socket`, one end of the connection. The client
creates its own `Socket` by naming the host and port. Each `Socket` has two streams: one to read
(`getInputStream()`), one to write (`getOutputStream()`). Example (a different case: a server that
just reports who connected):
```java
// Server program
ServerSocket server = new ServerSocket(5000);   // listen on port 5000
Socket client = server.accept();                // blocks here until a client connects
System.out.println("Connected from " + client.getInetAddress());

// Client program (a separate main)
Socket socket = new Socket("localhost", 5000);  // dials in; accept() above now returns
```
Docs: [ServerSocket](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/ServerSocket.html)

### Closing what you open (try-with-resources)
Sockets hold a real port in the operating system; if you don't close them, the port stays busy.
`try (...)` closes whatever you open inside the brackets when the block ends, even after an
exception. You may know this from Java 7/8 code at work.
```java
try (BufferedReader reader = new BufferedReader(new FileReader("notes.txt"))) {
    System.out.println(reader.readLine());
}   // reader.close() is called here automatically
```
Docs: [try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)

### Writing bytes (OutputStream)
Streams carry bytes, not Strings: turn text into bytes with `getBytes(StandardCharsets.UTF_8)`
before writing. In a test, `ByteArrayOutputStream` is a stream that just collects what you write in
memory, so you can check it, the mirror of the `ByteArrayInputStream` in your framer test.
```java
ByteArrayOutputStream out = new ByteArrayOutputStream();  // collects bytes in memory
out.write("hi".getBytes(StandardCharsets.UTF_8));         // write takes bytes (throws IOException)
System.out.println(out.toString(StandardCharsets.UTF_8)); // prints: hi
System.out.println(String.format("%03d", 7));             // prints: 007 (%03d = zero-pad to 3 digits)
```
Docs: [ByteArrayOutputStream](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/io/ByteArrayOutputStream.html)

## The payment idea
When you tap a card, the terminal builds an **authorization request (0100)** and sends it to the
acquirer; it travels through the network (mada, Visa…) to the **issuer**, which answers with a
**0110** whose **DE39 (response code)** says yes (`00`) or no. Every system that sends puts a
**length header** in front, because TCP is one continuous stream of bytes with no gaps between
messages. In this brief your terminal sim plays the terminal, and your switch plays the network.
Write the tap-a-card flow and why the length header exists in your own words in
`docs/payments-notes.md` (create it); I'll check it at the check-in.

## Decisions you'll face
### What the writer looks like and where it lives
Does it write straight onto an `OutputStream`, or return the framed text for someone else to
send? Is it a method next to `readMessage` in `MessageReader`, or its own class? This is a public
method the switch and the issuer sim will use later too, so it's hard to change.
My pick and why: in `transport`, next to `MessageReader`, because the issuer (and the switch) will need it later too.
Verdict (2026-11-14): sound pick. Stronger reason: otherwise the real switch would depend on the fake terminal; and the read and write sides of one format belong together, so a header change touches one place. Still open: return the framed text vs write onto a stream.

### A message too long for the header
The header has 4 digits, so the longest message it can describe is 9999. What should the writer
do if it gets a longer one?
My pick and why: throw an exception (A), otherwise a bug breaks the message flow and affects the next messages in the sequence.
Verdict (2026-11-14): sound, and the reason is exactly right: a wrong header desyncs the stream, so every later message is misread (fail fast). `IllegalArgumentException` is the standard exception for a bad argument. Not built yet: the check, plus a test that expects it (`assertThrows`).

## Traps
- **Starting the terminal first** → `ConnectException: Connection refused`. Nobody is waiting at the port yet. Start the switch first.
- **An old switch still running** → `BindException: Address already in use`. Stop it (red square in IntelliJ's Run panel) before starting a new one.
- **Switch hangs and prints nothing** → it's waiting for bytes that never come, usually because the header says more bytes than were sent. Print the header on both sides and compare.

## Hints
### Step 1
<details><summary>Level 1: a nudge</summary>In this step the switch has only one job: wait. You can prove it works before the terminal exists.</details>
<details><summary>Level 2: the idea or the API to look at</summary><code>new ServerSocket(port)</code> then <code>accept()</code>. Pick a port above 1024 (lower ones need admin rights) and give it a named constant. Print something before and after <code>accept()</code>. To knock without a terminal, run <code>nc localhost &lt;port&gt;</code> in a terminal window.</details>
<details><summary>Level 3: the shape</summary>In the switch's <code>main</code>: open a server socket on your port inside <code>try (...)</code> → print "listening" → call ______ and keep what it returns → print "connected".</details>

### Step 2
<details><summary>Level 1: a nudge</summary>The terminal is the client side of the sockets example. Your scope says sims live in the <code>sims</code> package.</details>
<details><summary>Level 2: the idea or the API to look at</summary><code>new Socket("localhost", port)</code> inside <code>try (...)</code>. Both programs need the same port number. Where should that constant live so they share it?</details>
<details><summary>Level 3: the shape</summary>A class with its own <code>main</code>: open a socket to ______ → print "connected". Run the switch, then this; the switch should print its "connected" line.</details>

### Step 3a
<details><summary>Level 1: a nudge</summary>Start with the test, not the code. Your framer test reads <code>"00100123456789"</code> and expects <code>"0123456789"</code>. The writer test is the same pair, the other way round.</details>
<details><summary>Level 2: the idea or the API to look at</summary><code>ByteArrayOutputStream</code> to collect what's written, <code>String.format</code> with <code>%04d</code> for the header, and <code>getBytes(StandardCharsets.UTF_8)</code>. Reuse <code>HEADER_LENGTH</code> instead of a new 4.</details>
<details><summary>Level 3: the shape</summary>Count the message's length → turn it into a ______-digit zero-padded header → write the header bytes → write the message bytes. Test: write into a <code>ByteArrayOutputStream</code>, assert its content equals the framed text.</details>

### Step 3b
<details><summary>Level 1: a nudge</summary>The socket's output stream is just another <code>OutputStream</code>, exactly the kind your test used.</details>
<details><summary>Level 2: the idea or the API to look at</summary><code>socket.getOutputStream()</code>, passed to your writer. Borrow the sample 0100 from <code>Main</code>.</details>
<details><summary>Level 3: the shape</summary>After connecting: get the output stream → give it and the sample message to ______ → print "sent".</details>

### Step 4
<details><summary>Level 1: a nudge</summary>You already wrote this part. It only needs a different <code>InputStream</code>.</details>
<details><summary>Level 2: the idea or the API to look at</summary>The <code>Socket</code> that <code>accept()</code> returned has <code>getInputStream()</code>. Hand it to <code>MessageFramer.readMessage</code>.</details>
<details><summary>Level 3: the shape</summary>After "connected": get the input stream → read one framed message with ______ → print the raw message to check it.</details>

### Step 5
<details><summary>Level 1: a nudge</summary>Your <code>Main</code> already does this with a hard-coded string.</details>
<details><summary>Level 2: the idea or the API to look at</summary><code>Codec.readMessage(text)</code>, then <code>getMti()</code>.</details>
<details><summary>Level 3: the shape</summary>Received text → ______ → print its MTI. Expect <code>0100</code>.</details>

## Come back when
- You've ticked everything in "Done when", or
- you've been stuck about 30 minutes after using the hints, or
- you hit a decision you can't make.

## From scratch (optional, 20–30 min, after this brief)
In an empty file in `~/Documents/Projects/java-practice/`: a server on port 6000 that accepts one
client and prints the first line it sends, and a client that sends `hello`. Hint: `BufferedReader.readLine()`
uses a newline as the end of a message, a different kind of framing.

## Check-in notes
- Step 1: port `5001` is a bare number in `Switch.listen`; the terminal will need the same one (Clean Code: replace magic numbers with named constants).
- Step 1: `ServerSocket` and `Socket` aren't in a `try (...)`, so they're only closed because the program ends. Matters once the switch keeps running.
- "waiting..." is printed *after* `accept()` returns, so it shows only once a terminal has connected; it belongs before `accept()`.
- Fixed: framing moved to `transport/MessageWriter` (test moved too, green); "waiting..." now before `accept()`.
- `Terminal` is now an empty class; `Connection` holds both `connect` and `writer` (connecting and writing are two jobs).
- The > 9999 check from decision 2 isn't built yet.
- Port `5001` now appears in both `Switch` and `SimRunner`: two copies that must stay in sync.
- The switch prints nothing until a terminal connects, so you can't tell "waiting" from "stuck" (Done-when item 2).
- `Terminal.framer` / `Terminal.writer` are nouns; methods should be verbs (Clean Code: method names are verbs).
- `String.format("%4s", n).replace(' ', '0')` works; `%04d` zero-pads in one step.
- `MessageFramerTest` still tests `MessageReader`; the test class name no longer matches.

## Check-in
**Checks run 2026-11-14:**
- `mvn test`: all green (`TerminalTest.framesMessage`, `CodecTest` ×2, `MessageFramerTest`).
- Real run (`Main`, then `SimRunner`): switch printed `Connected from /127.0.0.1` then `MTI: 0100`; both programs ended. Pass.
- Switch prints "waiting..." before a terminal connects: pass (after fix).
- Framing moved to `transport/MessageWriter`, `MessageWriterTest` green.
- Steps 1–5 committed in `07d7da5`; the `MessageWriter` move is not committed yet.

**Decisions:** both sound (verdicts under *Decisions you'll face*). Recorded in `scope.md`.

**Payment idea (explained back in chat):** flow in the right order; cryptogram, issuer checks and `00` = approved all right. Missing: names 0100/0110, DE39, the acquirer hop. Misconception fixed: the length header is a transport wrapper, not part of the ISO message. Why the header exists: solid (TCP is a byte stream with no boundaries).

**Tip:** method names should be verbs (Clean Code, ch. 2: "Methods should have verb or verb phrase names"). `MessageWriter.frame` → `frame`, `Connection.write` → `write`. IntelliJ: cursor on the name, Shift+F6 (Rename) updates every caller.

**Carry over:** the > 9999 check + `assertThrows` test; port as a shared constant; `try (...)` around sockets; empty `Terminal` class; `MessageFramerTest` name.
