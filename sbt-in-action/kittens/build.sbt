import scala.sys.process.Process

name := "preowned-kittens"
version := "1.0"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test",
  "org.specs2" %% "specs2-core" % "3.9.1" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
gitHeadCommitSha := Process("git rev-parse HEAD").lineStream.head

val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")
makeVersionProperties := {
  val propertiesFile = new File((resourceManaged in Compile).value, "version.properties")
  val content = s"version=${gitHeadCommitSha.value}"
  IO.write(propertiesFile, content)
  Seq(propertiesFile)
}