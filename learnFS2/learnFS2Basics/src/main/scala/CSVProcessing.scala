import cats.effect._
import cats.effect.Resource
import fs2.io.file.Files

import scala.io.Source
import scala.util.Try


object CSVProcessing extends  IOApp.Simple {

  case class LegoSet(id: String, name: String, year: Int, themeId: Int, numParts: Int)

  def readLegoSets(fileName: String) =
    Resource.make(IO(Source.fromFile(fileName))) { source =>
      IO(source.close())
    }.use { source =>
      IO(source.getLines().drop(1).foldLeft(Seq.empty[LegoSet])( (lego, line) => {
        Try{
          val Array(id, name, year, themeId, numParts) = line.split(",").map(_.trim)
          LegoSet(id, name, year.toInt, themeId.toInt, numParts.toInt)}.toOption match {
          case Some(legoSet) => lego :+ legoSet
          case None => lego
        }
      }))
    }


  def parseLogic(line: String) = {
    val splittedLine = line.split(",").map(_.trim)
    Try{
      LegoSet(splittedLine(0),
        splittedLine(1),
        splittedLine(2).toInt,
        splittedLine(3).toInt,
        splittedLine(4).toInt)
    }.toOption
  }

  def readLegoSet2(filePath: String) = {
    Files[IO].readAll(java.nio.file.Paths.get(filePath), 4096)
      .through(fs2.text.utf8Decode)
      .through(fs2.text.lines)
      .map(parseLogic)
      .collect{case Some(legoSet) => legoSet}
      .compile
      .toList
  }



  override def run: IO[Unit] = {
    readLegoSet2("./learnFS2Basics/src/main/resources/sets.csv").map(legoSets => {
      legoSets.foreach(println)
    })
  }

}
