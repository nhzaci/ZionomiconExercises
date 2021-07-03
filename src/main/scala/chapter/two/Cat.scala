package chapter.two

import zio._
import zio.console._
import scala.io.Source

import java.io.{File, IOException, PrintWriter}

object Cat extends App {
  def run(commandLineArguments: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      contents <- ZIO.foreach(commandLineArguments)(readFile)
      _ <- ZIO.foreach(contents)(printToConsole)
    } yield ()).exitCode

  def printToConsole(line: String): ZIO[Console, IOException, Unit] =
    for {
      _ <- putStrLn(line)
    } yield ()

  def writeFile(fileName: String, content: String): ZIO[Any, Throwable, Unit] =
    for {
      printWriter <- ZIO.effect(new PrintWriter(new File(fileName)))
      _ = printWriter.write(content)
      _ = printWriter.close()
    } yield ()

  def readFile(file: String): ZIO[Any, Throwable, String] =
    for {
      source <- ZIO.effect(Source.fromFile(file))
      sourceString = source.getLines.mkString
      _ = source.close
    } yield sourceString
}
