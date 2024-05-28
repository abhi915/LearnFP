package learn.proto

import scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import learn.proto.model.Address
import learn.proto.models.pb._

trait ModelPbSerializer extends Serializable {

  implicit def protoBufSerializer[A <: GeneratedMessage]: Serializer[A] =
    (msg: A) => msg.toByteArray

  implicit def protoBufDeserializer[A <: GeneratedMessage](implicit cmp: GeneratedMessageCompanion[A]): Deserializer[A] =
    (bytes) => cmp.parseFrom(bytes)

  implicit def addressSerializer: Serializer[Address] =
    Serializer[output.Address].contramap(PbConverters.from)

  implicit def addressDeserializer: Deserializer[Address] =
    Deserializer[output.Address].map(PbConverters.to)

}

object ModelPbSerializer extends ModelPbSerializer
