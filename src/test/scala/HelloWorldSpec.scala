import zio._
import zio.Console._
import zio.test._

object HelloWorld {
  def sayHello: ZIO[Console, Nothing, Unit] =
    printLine("Hello, World!").orDie
}

object HelloWorldSpec extends ZIOSpecDefault {
  import HelloWorld._

  def spec: Spec[TestEnvironment with Scope, Nothing] = suite("HelloWorldSpec")(
    test("sayHello correctly displays output") {
      for {
        _ <- sayHello
        output <- TestConsole.output
      } yield assertTrue(output == Vector("Hello, World!\n"))
    }
  ).provideEnvironment(DefaultServices.live)
}
