package learn.proto

trait Deserializer[A] extends Serializable {
  def deserialize(bytes: Array[Byte]): A
  def map[B](f: A => B): Deserializer[B] =
    (bytes: Array[Byte]) => f(Deserializer.this.deserialize(bytes))
}



object Deserializer  {
  def apply[A: Deserializer]: Deserializer[A] = implicitly[Deserializer[A]]
}