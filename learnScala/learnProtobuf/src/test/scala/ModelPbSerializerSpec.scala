package learn.proto.test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import  learn.proto.ModelPbSerializer
import learn.proto.model.Address
import learn.proto.JsonSerializer

/*
Address(1, Some("street"), "city")
size of byte array of protobuf < size of bytearray of json
 */

class ModelPbSerializerSpec extends AnyWordSpec with Matchers  {

 "Address should serialize to protobuf" in {
   val address = Address(1, Some("street"), "city")
   val serialized = ModelPbSerializer.addressSerializer.serialize(address)

   println(serialized.toList)

   val deserialized = ModelPbSerializer.addressDeserializer.deserialize(serialized)
    deserialized shouldBe address
 }

  "convert protobuf byteArray to Address" in {
    val addressProtobufArray = Array[Byte](8, 1, 18, 6, 115, 116, 114, 101, 101, 116, 26, 4, 99, 105, 116, 121)

    ModelPbSerializer.addressDeserializer.deserialize(addressProtobufArray) shouldBe Address(1, Some("street"), "city")
  }

  "convert addres to byteArray" in {

    val address = Address(1, Some("street"), "city")

    val data = JsonSerializer.AddressJsonSerializer.serialize(address).toList

    data shouldBe List(123, 34, 112, 111, 115, 116, 98, 111, 120, 34, 58, 49, 44, 34, 115, 116, 114, 101, 101, 116, 34, 58, 34, 115, 116, 114, 101, 101, 116, 34, 44, 34, 99, 105, 116, 121, 34, 58, 34, 99, 105, 116, 121, 34, 125)

    val deserialized = JsonSerializer.AddressJsonDeSerializer.deserialize(data.toArray)
    deserialized shouldBe address
  }

}
