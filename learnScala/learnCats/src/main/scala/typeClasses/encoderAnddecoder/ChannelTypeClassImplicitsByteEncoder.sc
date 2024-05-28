
import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using




trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}


object ByteEncoder  {
  implicit val stringByteEncoder: ByteEncoder[String] = instance[String](s => s.getBytes)

  implicit val intByteEncoder: ByteEncoder[Int] = instance[Int](n => {
    val bb = ByteBuffer.allocate(5)
    bb.putInt(n)
    bb.array()
  })

  implicit def optionByteEncoder[A](implicit encA: ByteEncoder[A]): ByteEncoder[Option[A]] = new ByteEncoder[Option[A]] {
    override def encode(a: Option[A]) = {
      a match {
        case Some(data) => encA.encode(data)
        case None => Array[Byte]()
      }
    }
  }

  // called summoner
  // summons instance in implicit scope
  // def apply[A](implicit ev: ByteEncoder[A]): ByteEncoder[A] = ev

  // helper method
  def instance[A](f: A => Array[Byte]): ByteEncoder[A] = new ByteEncoder[A] {
    override def encode(a: A) = f(a)
  }

  def apply[A: ByteEncoder] = implicitly[ByteEncoder[A]]
}





trait Channel {
  def write[A: ByteEncoder](a:A):Unit
}

object FileChannel extends Channel {
  override def write[A](a: A)(implicit enc : ByteEncoder[A]) = {
    val bytes = enc.encode(a)
    Using(new FileOutputStream("/Users/abhishekgopinath.patil/learnProj/cats/scalaCatsForNewbie/test", true)) { os =>
      println(bytes)
      os.write(bytes)
      os.flush
    }
  }
}

case class Switch(isOn: Boolean)
object Switch {
  implicit object switchEncoder extends ByteEncoder[Switch] {
    override def encode(a: Switch): Array[Byte] =
      Array(if(a.isOn) "1".toByte else "0".toByte)
  }
}


/*
// Use FileChannle
//FileChannel.write("hello")
FileChannel.write(Switch(true))
FileChannel.write(5)
FileChannel.write(Option("hello"))
 */
// Use as encoder
ByteEncoder[String].encode("hello")
ByteEncoder[Int].encode(5)


// Syntax: translation --> Impoertant concept
implicit  class ByteEncoderOps[T](a: T)(implicit encA: ByteEncoder[T]) {
  def encode: Array[Byte] = encA.encode(a)
}

"hello".encode
5.encode