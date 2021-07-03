package chapter.two

import zio._
import zio.console._

import java.io.{File, PrintWriter}
import scala.io.Source

object CopyFile extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      inputFileName <- getConsoleInput("Please input the file you would like to copy from: ") map (Constants.ASSETS_FOLDER + _)
      outputFileName <- getConsoleInput("Please input the name of the file to copy to: ") map (Constants.ASSETS_FOLDER + _)
      inputFileContents <- readFile(inputFileName)
      _ <- writeFile(outputFileName, inputFileContents)
    } yield ()).exitCode

  def getConsoleInput(message: String): URIO[Console, String] =
    for {
      input <- putStr(message) *> getStrLn <> getConsoleInput(message)
    } yield input

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
