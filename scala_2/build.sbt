name := "scala_2"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.1"

