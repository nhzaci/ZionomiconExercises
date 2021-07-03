package chapter.two

import scala.util.{Either, Failure, Success, Try}

final case class ZIO[-R, +E, +A](run: R => Either[E, A])

object ZIO {
  def zipWith[R, E, A, B, C](self: ZIO[R, E, A],
                             that: ZIO[R, E, B]
                            )(f: (A, B) => C): ZIO[R, E, C] =
    ZIO { r =>
      val eitherA: Either[E, A] = self.run(r)
      val eitherB: Either[E, B] = that.run(r)
      for {
        a <- eitherA
        b <- eitherB
      } yield f(a, b)
    }

  def collectAll[R, E, A](in: Iterable[ZIO[R, E, A]]): ZIO[R, E, List[A]] =
    ZIO { r =>
      def collectEffects(effects: List[ZIO[R, E, A]], acc: List[A]): Either[E, List[A]] = effects match {
        case head :: tail => head.run(r).flatMap(result => collectEffects(tail, result :: acc))
        case Nil => Right(acc)
      }
      collectEffects(in.toList, Nil)
    }

  def foreach[R, E, A, B](in: Iterable[A])(f: A => ZIO[R, E, B]): ZIO[R, E, List[B]] =
    collectAll(in.map(f))

  def orElse[R, E1, E2, A](self: ZIO[R, E1, A],
                           that: ZIO[R, E2, A]): ZIO[R, E2, A] =
    ZIO { r =>
      for {
        _ <- self.run(r).left
        right <- that.run(r)
      } yield right
    }
}