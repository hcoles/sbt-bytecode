package org.pitest.sbt.bytecode

import sbt._

private[sbt] case class PathSettings(reportPath : File, compiledPaths: Seq[File])

object BytecodeKeys {
  
  val bytecodeTask = TaskKey[Unit]("bytecode")
  
  val reportPath = SettingKey[File]("bytecode-target-path")
  
  private[sbt] val pathSettings = TaskKey[PathSettings]("bytecode-path-settings") 
  private[sbt] val compiledPaths = TaskKey[Seq[File]]("compiled-paths")

}