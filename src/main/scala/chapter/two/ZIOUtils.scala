package chapter.two

import zio._

object ZIOUtils {
  def eitherToZIO[E, A](either: Either[E, A]): ZIO[Any, E, A] = either match {
    case Right(value) => ZIO.succeed(value)
    case Left(exception) => ZIO.fail(exception)
  }

  def listToZIO[A](list: List[A]): ZIO[Any, None.type, A] = list match {
    case Nil => ZIO.fail(None)
    case head :: _ => ZIO.succeed(head)
  }


}
