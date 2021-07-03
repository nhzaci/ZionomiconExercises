package chapter.two

import zio._
import zio.console._

object NumberGuessing extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      randomNumber <- random
      guessCorrect <- getConsoleInput("Guess a number from 1 to 3: ") map (randomNumber.toString == _)
      _ <- putStrLn(
        if (guessCorrect) "You guessed right!"
        else s"You guessed wrong, the number was $randomNumber"
      )
    } yield ()).exitCode

  def getConsoleInput(message: String): URIO[Console, String] =
    for {
      input <- putStr(message) *> getStrLn <> getConsoleInput(message)
    } yield input

  lazy val random: Task[Int] = ZIO.effect(scala.util.Random.nextInt(3) + 1)
}
