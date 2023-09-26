package example

import scala.concurrent.{ExecutionContext, Future}

object AsyncMathLib:
  def square(x: Int)(using ExecutionContext): Future[Int] = Future(x * x)
