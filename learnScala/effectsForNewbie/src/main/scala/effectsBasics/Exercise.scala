package effectsBasics

import cats.effect._

import scala.io.Source
import scala.io.BufferedSource
import scala.util.Try
import java.nio.file.Path
/*
*  Implement load file function that load all content of file into string.'
*  If file does not exist capture them with domainError TextFileNotFound
* */



/*2
// use arg(0) file name (assume it always provided)
// Validate the filename and output any problem via console
// If loadeds successfully, output number of words in file (use service.countWords)
// If domain error occure communicate to user via console
// If technical something wrong happen, output "Something wrong" to console.
// If fatal error occure raise it
*/

import cats._
import cats.implicits._





class Exercise {




 // Should read file --> on success return string
 //                  --> on error return error
def readFile(fileName: String) = {

     val resource = Try{Source.fromResource(fileName)}.toEither
     def readFile(src: Source) = Try{src.getLines().mkString(" ")}.toEither
     def closeResource(src: Source) = Try{src.close()}.toEither

    for {
        source <- resource
        data   <-  readFile(source)
        _      <-  closeResource(source)
     } yield data


 }

}

trait Service

trait countWordService extends Service {
  def countWord(data: Iterator[String]) = data.foldLeft(0)((a, d) => a + d.split(" ").length)
}
