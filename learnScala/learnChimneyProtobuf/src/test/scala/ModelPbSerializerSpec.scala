import learn.proto.{Address, Deserializer, ModelPbSerializer, Serializer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

/*
Address(1, Some("street"), "city")
size of byte array of protobuf < size of bytearray of json
 */

class ModelPbSerializerSpec extends AnyWordSpec with Matchers with ModelPbSerializer {

 "Address should serialize to protobuf" in {
   val address = Address(1, Some("street"), "city")
   val serialized = Serializer[Address].serialize(address)

   println(serialized.toList)

   val deserialized = Deserializer[Address].deserialize(serialized)
    deserialized shouldBe address
 }

  "convert protobuf byteArray to Address" in {
    val addressProtobufArray = Array[Byte](8, 1, 18, 6, 115, 116, 114, 101, 101, 116, 26, 4, 99, 105, 116, 121)

   Deserializer[Address].deserialize(addressProtobufArray) shouldBe Address(1, Some("street"), "city")
  }


}
