import sttp.client4.quick.*
import sttp.client4.Response
import sttp.model.Uri

// val response: Response[String] = quickRequest
//   .get(uri"https://httpbin.org/get")
//   .send()

// response.code
// response.body

// val request = quickRequest
//   .get(uri"https://example.com")
//   .header("Origin", "https://scala-lang.org")

// request.headers

// val request2 = quickRequest
//   .get(uri"https://example.com")
//   .auth
//   .basic(user = "user", password = "***")

// val book = "programming in scala"
// val bookUri: Uri = uri"https://example.com/books/$book"
// bookUri

// val queryParams = Map(
//   "q" -> "scala",
//   "limit" -> "10",
//   "page" -> "1"
// )
// val uriWithQueryParams = uri"https://example.com/search?$queryParams"
// uriWithQueryParams

// def getUri(limit: Option[Int]): Uri = uri"https://example.com/all?limit=$limit"
// getUri(Some(10))
// getUri(None)

// def getUri2(versions: Seq[String]): Uri =
//   uri"https://example.com/scala?version=$versions"

// getUri2(Seq("3.2.2"))
// getUri2(Seq("2.13.8", "2.13.9", "2.13.10"))
// getUri2(Seq.empty)

// val response2 = quickRequest
//   .post(uri"https://example.com/")
//   .body("Lorem ipsum")
//   .send()

// response2.code

// val bytes: Array[Byte] = "john".getBytes
// val request3 = quickRequest.post(uri"https://example.com/").body(bytes)

// val json = ujson.Obj(
//   "location" -> "hometown",
//   "bio" -> "Scala programmer"
// )

// val response3 = quickRequest
//   .patch(uri"https://api.github.com/user")
//   .auth
//   .bearer(sys.env("GITHUB_TOKEN"))
//   .header("Content-Type", "application/json")
//   .body(ujson.write(json))
//   .send()

// response3.code
// response3.body

// val response3 = quickRequest
//   .get(uri"https://api.github.com/user")
//   .auth
//   .bearer(sys.env("GITHUB_TOKEN"))
//   .send()

// val json2 = ujson.read(response3.body)
// json2("login").str

// val file: java.nio.file.Path = (os.pwd / "image.png").toNIO
// val response4 = quickRequest.post(uri"https://example.com/").body(file).send()

// response4.code

val file1 = (os.pwd / "avatar1.png").toNIO
val file2 = (os.pwd / "avatar2.png").toNIO
val response5 = quickRequest
  .post(uri"https://example.com/")
  .multipartBody(
    multipartFile("avatar1.png", file1),
    multipartFile("avatar2.png", file2)
  )
  .send()
