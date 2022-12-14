# jdump

jdump is a tool that creates multiple forms of dumps for multiple OpenJDK HotSpot JVMs running on the local system in
one go. This might make it easier to request detailed JVM information for debugging issues on customer sites etc.
without needing to brief them on the details of using jcmd or similar tools.

`java -jar jdump -A` for example will provide all supported types of dumps for all running JVMs that it manages to
connect to using the Attach API.

## Supported jdump.dump types

Currently supported are

* Heap dumps
* Thread dumps (stack traces of all running threads)
* Java Flight Recordings

## Usage
```
java -jar jdump.jar [-f<folder name>] [-A] [-H] [-J] [-d<duration in seconds>] [-M] [-N] [-T]

Options:
start without options to show UI, if not on a headless system
-f<name>: name of the target folder (will be created if non-existent; current working directoy, if empty)
-A: produce all types of dumps for all JVMs running locally
-H: produce heap dumps for all JVMs running locally
-J: produce JFRs for all JVMs running locally
-M: produce mallinfo() stats on Linux with glibc
-d<duration in seconds>: the duration selected for the JFRs, in seconds, default: 5
-N: product Native Memory Tracks for all JVMs running locally
-T: produce thread dumps for all JVMs running locally

```

## Development

To build and run the tool for testing, run ```./gradlew run --args="<tool arguments>"``` with the desired tool
arguments.

To build the tool into a jar file, run ```./gradlew jar```, the jar file will be generated in build/libs.

To build and run the tool into a standalone custom JVM, run ```./gradlew jlink```, the image will be generated in
image/. Copy it to your target system and execute image/bin/jdump to run the application.