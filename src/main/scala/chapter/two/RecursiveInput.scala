package chapter.two

import zio.{Exit, _}
import zio.console._

import java.io.IOException

object RecursiveInput extends App {
  def readUntil(acceptInput: String => Boolean): ZIO[Console, IOException, String] =
    for {
      input <- getStrLn >>> {
        case string if acceptInput(string) => putStrLn("Input accepted!") *> string
        case _ => putStrLn("Input is rejected. Please try again.") *> readUntil(acceptInput)
      }
    } yield input

  def doWhile[R, E, A](body: ZIO[R, E, A])(condition: A => Boolean): ZIO[R, E, A] =
    for {
        result <- body >>> {
          case value if condition(value) => value
          case _ => doWhile(body)(condition)
        }
    } yield result
}
