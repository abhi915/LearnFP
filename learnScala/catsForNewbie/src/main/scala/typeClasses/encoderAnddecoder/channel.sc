import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using


trait Channel {
  def write(obj: ByteEncodable) : Unit
}

object FileChannel extends Channel {
  override def write(obj: ByteEncodable): Unit = {
    val bytes : Array[Byte] = obj.encode()
    Using(new FileOutputStream("scalaCatsForNewbie/test")){ os =>
      os.write(bytes)
      os.flush()
    }
  }
}

trait ByteEncodable {
  def encode(): Array[Byte]
}

case class FullName(firstName: String, lastName: String) extends ByteEncodable {
  override def encode(): Array[Byte] = (firstName + " " + lastName).getBytes
 }

