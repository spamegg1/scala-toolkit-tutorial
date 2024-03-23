import scala.collection.mutable
import upickle.default.*

val jsonString =
  """{"name": "Peter", "age": 13, "pets": ["Toolkitty", "Scaniel"]}"""
val json: ujson.Value = ujson.read(jsonString) // Parse the JSON string
json("name").str

val pets: ujson.Value = json("pets")
val firstPet: String = pets(0).str
val secondPet: String = pets(1).str
s"The pets are $firstPet and $secondPet"

val person: mutable.Map[String, ujson.Value] = json.obj
val age: Double = person("age").num
val pets2: mutable.Buffer[ujson.Value] = person("pets").arr

try person("name").bool
catch case _ => "throws ujson.Value.InvalidData"

json("name") = "Peter" // Update it
json("nickname") = "Pete"
json("pets").arr.remove(1)

val result: String = ujson.write(json) // Write it back to a String
// result

val json2 = """{"primes": [2, 3, 5], "evens": [2, 4, 6]} """
val map: Map[String, List[Int]] = read[Map[String, List[Int]]](json2)
map("primes")

val json3 = """{"name": "Peter"} """

try read[Map[String, List[Int]]](json3)
catch
  case _ =>
    "throws upickle.core.AbortException"

case class PetOwner(name: String, pets: List[String]) derives ReadWriter

val json4 = """{"name": "Peter", "pets": ["Toolkitty", "Scaniel"]}"""
val petOwner: PetOwner = read[PetOwner](json4)
val firstPet2 = petOwner.pets.head
s"${petOwner.name} has a pet called $firstPet2"

val map2: Map[String, Int] = Map("Toolkitty" -> 3, "Scaniel" -> 5)
val jsonString2: String = write(map2)
// jsonString2

val petOwner2 = PetOwner("Peter", List("Toolkitty", "Scaniel"))
val json5: String = write(petOwner)
// json5

val json6 = ujson.read(os.read(os.pwd / "raw.json"))
json6("updated") = "now" // modify the JSON content
// os.write(os.pwd / "raw-updated.json", ujson.write(json6)) // write to a new file

val petOwner3: PetOwner = read[PetOwner](os.read(os.pwd / "pet-owner.json"))
val petOwnerUpdated = petOwner3.copy(pets = "Toolkitty" :: petOwner3.pets)
// os.write(os.pwd / "pet-owner-updated.json", write(petOwnerUpdated))

val obj: ujson.Value = ujson.Obj(
  "library" -> "upickle",
  "versions" -> ujson.Arr("1.6.0", "2.0.0", "3.1.0"),
  "documentation" -> "https://com-lihaoyi.github.io/upickle/"
)

case class Bar(i: Int, s: String)

object Bar:
  given ReadWriter[Bar] = readwriter[ujson.Value]
    .bimap[Bar](
      x => ujson.Arr(x.s, x.i),
      json => new Bar(json(1).num.toInt, json(0).str)
    )

val bar = Bar(5, "bar")
val json7 = upickle.default.write(bar)
// json7
