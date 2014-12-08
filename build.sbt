sbtPlugin := true

name := "sbt-bytecode"

organization := "org.pitest.sbt"

version := "0.1"

resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies += "org.ow2.asm" % "asm-util" % "4.0"

libraryDependencies += "org.pitest" % "highwheel-parser" % "1.0"
