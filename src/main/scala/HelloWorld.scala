import zio._
import zio.console._

object HelloWorld extends App {
  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    myAppLogic *> ZIO.succeed(ExitCode.success)

  val myAppLogic =
    for {
      _ <- putStrLn("Hello World!")
    } yield ()

}
