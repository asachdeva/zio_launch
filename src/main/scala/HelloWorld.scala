import zio._
import zio.console._

object HelloWorld extends App {
  def run(args: List[String]): URIO[ZEnv, ExitCode] =
    myAppLogic.exitCode

  val myAppLogic =
    for {
      _ <- putStrLn("Hello World!")
    } yield ()

}
