package chapter.two

import zio._
import zio.console._

object WrappingEffects extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      currentTime <- getCurrentTime
      _ <- putStrLn(s"The current time is $currentTime")
    } yield ()).exitCode

  lazy val getCurrentTime: UIO[Long]= ZIO.effectTotal(System.currentTimeMillis())

  def getCacheValue(key: String,
                    onSuccess: String => Unit,
                    onFailure: Throwable => Unit): Unit = ???

  def getCacheValueZio(key: String): ZIO[Any, Throwable, String] =
    ZIO.effectAsync[Any, Throwable, String] { register =>
      getCacheValue(key, string => register(ZIO.succeed(string)), exception => register(ZIO.fail(exception)))
    }


  trait User
  def saveUserRecord(user: User,
                     onSuccess: () => Unit,
                     onFailure: Throwable => Unit): Unit = ???

  def saveUserRecordZio(user: User): ZIO[Any, Throwable, Unit] =
    ZIO.effectAsync[Any, Throwable, Unit] { register =>
      saveUserRecord(user, () => register(ZIO.succeed(())), exception => ZIO.fail(exception))
    }
}