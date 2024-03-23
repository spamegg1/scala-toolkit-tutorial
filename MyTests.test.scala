package example

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Properties
import java.nio.file.NoSuchFileException

class MyTests extends munit.FunSuite:
  test("sum of two integers"):
    val obtained = 2 + 2
    val expected = 4
    assertEquals(obtained, expected)

  test("all even numbers"):
    val input: List[Int] = List(1, 2, 3, 4)
    val obtainedResults: List[Int] = input.map(_ * 2)
    // check that obtained values are all even numbers
    assert(obtainedResults.forall(x => x % 2 == 0))

  test("failing test".ignore):
    val obtained = 2 + 3
    val expected = 4
    assertEquals(obtained, expected)

  test("requests".flaky.ignore):
    // I/O heavy tests that sometimes fail
    ???

class MathSuite extends munit.FunSuite:
  test("addition"):
    assert(1 + 1 == 2)

  test("multiplication".only):
    assert(3 * 7 == 21)

  test("remainder"):
    assert(13 % 5 == 3)

class FileTests extends munit.FunSuite:
  test("read missing file"):
    val missingFile = os.pwd / "missing.txt"
    // the code that should throw an exception
    val exception = intercept[NoSuchFileException](os.read(missingFile))
    assert(clue(exception.getMessage).contains("missing.txt"))

  val usingTempFile: FunFixture[os.Path] = FunFixture(
    setup = _ => os.temp(prefix = "file-tests"),
    teardown = tempFile => os.remove(tempFile)
  )

  usingTempFile.test("overwrite on file"): tempFile =>
    os.write.over(tempFile, "Hello, World!")
    val obtained = os.read(tempFile)
    assertEquals(obtained, "Hello, World!")

  val using2TempFiles: FunFixture[(os.Path, os.Path)] =
    FunFixture.map2(usingTempFile, usingTempFile)

  using2TempFiles.test("merge two files".ignore): (file1, file2) =>
    // body of the test
    ???

  test("home directory"):
    assume(Properties.isLinux, "this test runs only on Linux")
    assert(os.home.toString.startsWith("/home/"))

class AsyncMathLibTests extends munit.FunSuite:
  test("square"):
    for
      squareOf3 <- AsyncMathLib.square(3)
      squareOfMinus4 <- AsyncMathLib.square(-4)
    yield
      assertEquals(squareOf3, 9)
      assertEquals(squareOfMinus4, 16)
