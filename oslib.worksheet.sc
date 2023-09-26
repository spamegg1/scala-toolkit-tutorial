val etc: os.Path = os.root / "etc"
val entries: Seq[os.Path] = os.list(os.root / "etc")
val dirs: Seq[os.Path] = os.list(os.root / "etc").filter(os.isDir)

val path: os.Path = os.root / "usr" / "share" / "dict" / "words"
val content: String = os.read(path)
val lines: Seq[String] = os.read.lines(path)
lines.maxBy(_.size)

val lineStream: geny.Generator[String] = os.read.lines.stream(path)
val firstLine: String = lineStream.head

val path2: os.Path = os.temp.dir() / "output.txt"
os.write(path2, "hello\nthere\n")
os.read.lines(path2).size

try os.write(path2, "this will fail")
catch case _ => "yup it failed!"

os.write.append(path2, "two more\nlines\n")
os.read.lines(path2).size

val path3: os.Path = os.pwd / "output.txt"
os.exists(path3) // this was false at first, then file is created below
val result: os.CommandResult = os.proc("touch", path3).call()
println(result.exitCode)
os.exists(path3)

val result2: os.CommandResult = os.proc("expr", "2", "+", "2").call()
val text = result2.out.text()
text.trim.toInt

val result3: os.CommandResult = os.proc("ncal", "-h", "2", "2023").call()
result3.out.lines().foreach(println)
