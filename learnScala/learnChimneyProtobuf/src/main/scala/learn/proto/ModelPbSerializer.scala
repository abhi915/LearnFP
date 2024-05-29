package learn.proto

import io.scalaland.chimney.PartialTransformer
import scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import learn.proto.models.pb._
import io.scalaland.chimney.partial.Result

trait ModelPbSerializer extends LowPriorityTransformerSerializers with PbTransformer with Serializable {

  implicit def protoBufSerializer[A <: GeneratedMessage]: Serializer[A] =
    (msg: A) => msg.toByteArray

  implicit def protoBufDeserializer[A <: GeneratedMessage](implicit cmp: GeneratedMessageCompanion[A]): Deserializer[A] =
    (bytes) => cmp.parseFrom(bytes)

}

object ModelPbSerializer extends ModelPbSerializer

trait LowPriorityTransformerSerializers {

  implicit def canBeTransformedIntoSerializable[A, B](implicit
                                                      transformer: PartialTransformer[B, A],
                                                      a: Serializer[A]): Serializer[B] =
    a.contramap(value => getOrFail(transformer.transformFailFast(value)))

  implicit def canBeTransformedFromDeserializable[A, B](implicit
                                                        transformer: PartialTransformer[A, B],
                                                        a: Deserializer[A]): Deserializer[B] =
    a.map(value => getOrFail(transformer.transformFailFast(value)))

  private def getOrFail[A](result: Result[A]): A =
    result.asEither.fold(
      errors =>
        throw new RuntimeException(s"""Transformation failed with: ${errors.asErrorPathMessages.mkString("; ")}"""),
      identity)
}
