# jdump

jdump is a tool that creates multiple forms of dumps for multiple JVMs running on the local system in one go. This might
make it easier to request detailed JVM information for debugging issues on customer sites etc. without needing to brief
them on the details of using jcmd or similar tools.

`jdump -A` for example will provide all supported types of dumps for all running JVMs that it manages to connect to using the
Attach API.

## Supported dump types

Currently supported are

* Heap dumps
* Thread dumps (stack traces of all running threads)
* Java Flight Recordings

## Usage
```
jdump [-A] [-H] [-T] [-J] [-d<duration in seconds>]

Options:
-A: produce all types of dumps for all JVMs running locally
-H: produce heap dumps for all JVMs running locally
-T: produce thread dumps for all JVMs running locally
-J: produce JFRs for all JVMs running locally
-d<duration in seconds>: the duration selected for the JFRs, in seconds, default: 5
```