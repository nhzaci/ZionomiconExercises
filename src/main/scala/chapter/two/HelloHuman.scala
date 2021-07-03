package chapter.two

import zio._
import zio.console._

import java.io.IOException

object HelloHuman extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      name <- getConsoleInput("What's your name? ")
      _ <- greet(name)
    } yield ()).exitCode

  def getConsoleInput(message: String): URIO[Console, String] =
    for {
      name <- putStr(message) *> getStrLn <> getConsoleInput(message)
    } yield name

  def greet(name: String): ZIO[Console, IOException, Unit] =
    for {
      _ <- putStr(s"Welcome, $name")
    } yield ()
}
