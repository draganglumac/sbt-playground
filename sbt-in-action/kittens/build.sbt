import scala.sys.process.Process

name := "preowned-kittens"

// Custom keys for this build

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")

// Common settings/definitions for the build

def PreownedKittenProject(name: String): Project = {
  Project(name, file(name)).
  settings(
    version := "1.0",
    organization := "com.preownedkittens",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.12" % "test",
      "org.specs2" %% "specs2-core" % "3.9.1" % "test"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos")
  )
}

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lineStream.head

// Projects in build

lazy val common = (
  PreownedKittenProject("common").
  settings(
    makeVersionProperties := {
      val propertiesFile = new File((resourceManaged in Compile).value, "version.properties")
      val content = s"version=${gitHeadCommitSha.value}"
      IO.write(propertiesFile, content)
      Seq(propertiesFile)
    }
  )
)

lazy val analytics = (
  PreownedKittenProject("analytics").
  dependsOn(common).
  settings()
)

lazy val website = (
  PreownedKittenProject("website").
  dependsOn(common).
  settings()
)
