import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using

trait ByteEncoder[A] {
  def encode(a:A) : Array[Byte]
}

trait Channel {
  def write[A](obj: A, enc: ByteEncoder[A]): Unit
}

object FileChannel extends Channel {
  override def write[A](obj: A, enc: ByteEncoder[A]) = {
    val bytes : Array[Byte] = enc.encode(obj)
    Using(new FileOutputStream("scalaCatsForNewbie/test")){os =>
          os.write(bytes)
          os.flush
    }
  }
}

object IntByteEncoder extends ByteEncoder[Int] {
  override def encode(n: Int): Array[Byte] = {
     val bb = ByteBuffer.allocate(5)
     bb.putInt(n)
     bb.array()
  }
}

object StringByteEncoder extends ByteEncoder[String] {
  override def encode(data: String) : Array[Byte] = data.getBytes
}



FileChannel.write[Int](5, IntByteEncoder)
FileChannel.write[String]("hello", StringByteEncoder)