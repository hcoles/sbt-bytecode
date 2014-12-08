sbt-bytecode
============

Quick and dirty sbt plugin to dump dissassembled bytecode to disk.

## Setup

Add plugin to project/plugins.sbt

``` scala
addSbtPlugin("org.pitest.sbt" % "sbt-bytecode" % "0.1")
```

Setup properties in build.sbt

``` scala
import org.pitest.sbt._

bytecodeSettings
```


