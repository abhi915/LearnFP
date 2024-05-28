import org.json4s._
import org.json4s.native.JsonMethods._

parse(""" { "numbers" : [1, 2, 3, 4] } """)

parse("""{"name":"Toy","price":35.35}""", useBigDecimalForDouble = true)


// jackson support

def parseJson(json: String): JValue = {
  import org.json4s.jackson.JsonMethods._
  parse(json)
}

parseJson(""" { "numbers" : [1, 2, 3, 4] } """)

// DSL rule
import org.json4s.JsonDSL._
import org.json4s.JsonDSL._
val json = List(1, 2, 3)
compact(render(json))

val json = ("name" -> "joe")
compact(render(json))


val json = ("name" -> "joe") ~~ ("age" -> 35)
compact(render(json))


val json = ("name" -> "joe") ~ ("age" -> Some(35))
compact(render(json))

// Querying json linq style
import org.json4s._
import org.json4s.native.JsonMethods._
val json1 = parse("""
         { "name": "joe",
           "children": [
             {
               "name": "Mary",
               "age": 5
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """)

for {
  JObject(child) <- json1
  JField("age", JInt(age))  <- child
} yield age

for {
  JObject(child) <- json1
  JField("name", JString(name)) <- child
  JField("age", JInt(age)) <- child
  if age > 4
} yield (name, age)


// XPath + HOFs

import org.json4s._
import org.json4s.JsonDSL._
// parse to JObject
val jsonNew = parse("""{
                |  "person": {
                |    "name": "Joe",
                |    "age": 35,
                |    "spouse": {
                |      "person": {
                |        "name": "Marilyn",
                |        "age": 33
                |      }
                |    }
                |  }
                |}""".stripMargin)
// use DXPATH + HOF
jsonNew \\ "spouse"

// case classes can be used to extract value from json

case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
case class Address(street: String, city: String)
case class Person(name: String, address: Address, children: List[Child])

def learnExtraction = {

  import org.json4s._
  import org.json4s.jackson.JsonMethods._


  implicit val formats: Formats = DefaultFormats // Brings in default date formats etc.

  val jsonData = parse(
    """
         { "name": "joe",
           "address": {
             "street": "Bulevard",
             "city": "Helsinki"
           },
           "children": [
             {
               "name": "Mary",
               "age": 5,
               "birthdate": "2004-09-04T18:06:22Z"
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """)

  println(jsonData.extract[Person])



  val addressJson = jsonData  \ "address"  // Extract address object
  addressJson.extract[Address]
}

learnExtraction
