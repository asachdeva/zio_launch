import sbt._

object Dependencies {

  object Versions {
    val catsEffect = "2.1.3"
    val zioCats = "2.2.0.1"
    val zio = "1.0.2"

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
