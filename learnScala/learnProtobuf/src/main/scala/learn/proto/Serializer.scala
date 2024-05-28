package learn.proto

trait Serializer[A] extends Serializable {
  def serialize(value: A): Array[Byte]
  def contramap[B](f: B => A): Serializer[B] =
    (value: B) => Serializer.this.serialize(f(value))
}

object Serializer  {
  def apply[A: Serializer]: Serializer[A] = implicitly[Serializer[A]]
}
