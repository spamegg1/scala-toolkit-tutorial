import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import sttp.client4.*
import sttp.ws.WebSocket

val asyncBackend = DefaultFutureBackend()
val response6: Future[Response[String]] = quickRequest
  .get(uri"https://example.com")
  .send(asyncBackend)

// val asyncBackend = DefaultFutureBackend()

def useWebSocket(ws: WebSocket[Future]): Future[Unit] =
  for
    _ <- ws.sendText("Hello")
    text <- ws.receiveText()
  yield println(text)

val response = quickRequest
  .get(uri"wss://ws.postman-echo.com/raw")
  .response(asWebSocketAlways(useWebSocket))
  .send(asyncBackend)

Await.result(response, Duration.Inf)
