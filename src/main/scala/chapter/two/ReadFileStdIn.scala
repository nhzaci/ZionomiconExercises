package chapter.two

import zio._

object ReadFileStdIn extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      line <- readLine
      _ <- printLine(line)
    } yield ()).exitCode

  lazy val readLine: Task[String] = ZIO.effect(scala.io.StdIn.readLine())

  def printLine(line: String): Task[Unit] = ZIO.effect(println(line))
}
