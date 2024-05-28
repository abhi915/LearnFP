import java.nio.ByteBuffer


trait ByteDecoder[A] {
  def decode(data: Array[Byte]): Option[A]
}

object ByteDecoder {
  implicit val stringDecoder: ByteDecoder[String] = instance[String](data=> Some(new String(data)))


  implicit val intDecoder: ByteDecoder[Int] = instance[Int]{ data =>
      if(data.length != 4)  None
      else {
        val bb = ByteBuffer.wrap(data)
        Some(bb.getInt())
      }
     }

  def apply[T](implicit ev: ByteDecoder[T]) = ev

  def instance[T](f: Array[Byte] => Option[T]) = new ByteDecoder[T] {
    override def decode(data: Array[Byte]): Option[T] = f(data)
  }

}

ByteDecoder[String].decode("Hello".getBytes())

ByteDecoder[Int].decode(
  {
    val bb = ByteBuffer.allocate(4)
    bb.putInt(20)
    bb.array()
}
)
