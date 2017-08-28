import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    hello := { println("Hello!") },
    libraryDependencies += scalaTest % Test
  )

lazy val hello = taskKey[Unit]("An example task")
