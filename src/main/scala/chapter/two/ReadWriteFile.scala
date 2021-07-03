package chapter.two

import zio._
import zio.console._

import java.io.{File, PrintWriter}

object ReadWriteFile extends App {
  override def run(args: List[String]): URIO[Console, ExitCode] =
    (for {
      fileName <- getConsoleInput("Please enter a file name for the new file: ") map (Constants.ASSETS_FOLDER + _)
      fileContent <- getConsoleInput("Please enter what you would like to write in the file: ")
      _ <- writeFile(fileName, fileContent) *> putStr("Write complete!")
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
}
