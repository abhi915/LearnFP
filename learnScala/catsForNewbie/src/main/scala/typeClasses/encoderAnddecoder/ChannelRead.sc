

import java.io.FileInputStream
import scala.util.Using
import java.io.File
import java.nio.file.Paths
import java.nio.charset.StandardCharsets


trait ByteDecoder[A]{
  def decode(byte: Array[Byte]): A
}

object ByteDecoder{

  implicit val stringByteDecoder: ByteDecoder[String] = instance[String](byte => new String(byte, StandardCharsets.UTF_8))

 def apply[A](implicit ev: ByteDecoder[A]): ByteDecoder[A] = ev

  def instance[A](f: Array[Byte] => A) = new ByteDecoder[A] {
    override def decode(byte: Array[Byte]) = f(byte)
  }
}

trait channel {
  def read[A](data: Array[Byte])(implicit dec: ByteDecoder[A]):A
}

object UserInput extends channel {

  def read[A](bytes: Array[Byte])(implicit dec: ByteDecoder[A]): A = dec.decode(bytes)
}

UserInput.read[String]("Hello Akka".getBytes)


