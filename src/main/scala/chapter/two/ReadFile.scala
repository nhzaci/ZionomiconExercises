package chapter.two

import zio._
import zio.console._

import scala.io.Source

object ReadFile extends App {
  def run(args: List[String]): URIO[Console, ExitCode] =
    (for {
      fileName <- getFileName
      fileString <- readFile(fileName)
      _ <- putStrLn(fileString)
    } yield ()).exitCode

  def getFileName: URIO[Console, String] =
    for {
      fileName <- putStr("""Please enter the file name from "~/assets" folder: """) *> getStrLn <> getFileName
    } yield Constants.ASSETS_FOLDER + fileName

  def readFile(file: String): ZIO[Any, Throwable, String] =
    for {
      source <- ZIO.effect(Source.fromFile(file))
      sourceString = source.getLines.mkString
      _ = source.close
    } yield sourceString
}
