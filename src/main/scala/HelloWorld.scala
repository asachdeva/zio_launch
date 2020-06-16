import zio._

object HelloWorld extends App {
  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    myAppLogic *> ZIO.succeed(ExitCode.success)

  val myAppLogic = console.putStrLn("Hello World")
}
