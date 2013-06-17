package org.pitest.sbt.bytecode

import sbt._
import Keys._
import BytecodeKeys._
import org.pitest.highwheel.classpath._
import java.io.File
import inc.Locate
import java.io.IOException
import java.net.URLDecoder
import java.util.Collection
import java.util.ArrayList
import scala.collection.JavaConversions._
import org.pitest.highwheel.model.ElementName
import java.io.FileOutputStream
import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.TraceClassVisitor
import org.objectweb.asm.util.Textifier
import java.io.PrintWriter


object BytecodePlugin extends Plugin {

  lazy val bytecodeSettings = Seq(

    reportPath <<= crossTarget(_ / "bytecode"),
    compiledPaths <<= classDirectory in Compile map (f => Seq(f)),
    pathSettings <<= (reportPath, compiledPaths) map PathSettings dependsOn (compile in Compile),

    bytecodeTask <<= (pathSettings) map analyse)

  def analyse(paths : PathSettings) = {
    val roots = paths.compiledPaths map { f => new DirectoryClassPathRoot(f)}
    val cpr = new CompoundClassPathRoot(roots)
    
    println("Results will be written to " + paths.reportPath)
    println("Analysing " + paths.compiledPaths.size + " directories ")
    paths.compiledPaths foreach (println)
    
    val elements = collectionAsScalaIterable( cpr.classNames() )
    
    elements.foreach (dumpByteCode (paths.reportPath)(cpr))
     
  }
  
  def dumpByteCode (dir : File) (cpr : ClasspathRoot) ( e : ElementName) = {
    val outDir = new File(dir + File.separator
            + e.getParent().asJavaName().replace(".", File.separator));
    
    println("Analysing " + e.asJavaName() )
    
    outDir.mkdirs();
    
    val fos = new FileOutputStream(outDir + File.separator + e.getNameWithoutPackage().asJavaName() + ".bytecode");
    
    try {
          val reader = new ClassReader(cpr.getData(e));
          reader.accept(new TraceClassVisitor(null, new Textifier(),
              new PrintWriter(fos)), 0);

    } finally {
      fos.close()
    }
    
  }
  

}