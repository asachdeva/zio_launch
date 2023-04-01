import sbt._

object Dependencies {

  object Versions {
    val catsEffect = "2.5.1"
    val zioCats = "23.0.03"
    val zio = "2.0.10"

    // Test

    // Compiler
    val kindProjector = "0.11.0"
    val betterMonadicFor = "0.3.1"

    // Runtime
    val logback = "1.2.3"
  }

  object Libraries {
    def zio(artifact: String, version: String): ModuleID = "dev.zio" %% artifact % version

    lazy val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

    lazy val zioCore = zio("zio", Versions.zio)
    lazy val zioCats = zio("zio-interop-cats", Versions.zioCats)

    // Compiler
    lazy val kindProjector = ("org.typelevel" %% "kind-projector" % Versions.kindProjector).cross(CrossVersion.full)
    lazy val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
    lazy val zioTest = zio("zio-test", Versions.zio)
    lazy val zioTestSbt = zio("zio-test-sbt", Versions.zio)
    lazy val zioTestMagnolia = zio("zio-test-magnolia", Versions.zio)
  }

}
