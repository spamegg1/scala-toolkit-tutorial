package example

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

object AsyncMathLib:
  def square(x: Int)(using ExecutionContext): Future[Int] = Future(x * x)

@main
def run = println(AsyncMathLib.square(2))
