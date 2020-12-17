import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

name := """zio_launch"""
organization in ThisBuild := "asachdeva"

val format = taskKey[Unit]("Format files using scalafmt and scalafix")
promptTheme := PromptTheme(
  List(
    text(_ => "[asachdeva]", fg(64)).padRight(" λ ")
  )
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  skip.in(publish) := true,
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  fork := true
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

lazy val `zio_launch` = project
  .in(file("."))
  .settings(
    testSettings,
    organization := "asachdeva",
    name := "zio_launch",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.4",
    libraryDependencies ++= Seq(
      Libraries.zioCore,
      Libraries.zioCats,
      Libraries.zioTest % Test,
      Libraries.zioTestSbt % Test,
      Libraries.zioTestMagnolia % Test
    ),
    addCompilerPlugin(Libraries.betterMonadicFor),
    format := {
      Command.process("scalafmtAll", state.value)
      Command.process("scalafmtSbt", state.value)
    }
  )

// CI build
addCommandAlias("buildZiolaunch", ";clean;+test;")
