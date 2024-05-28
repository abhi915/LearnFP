package learn.proto.test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import  learn.proto.ModelPbSerializer
import learn.proto.model.Address


class ModelPbSerializerSpec extends AnyWordSpec with Matchers with ModelPbSerializer {

 "Address should serialize to protobuf" in {
   val address = Address(1, Some("street"), "city")
   val serialized = addressSerializer.serialize(address)

   println(serialized.toList)

   val deserialized = addressDeserializer.deserialize(serialized)
    deserialized shouldBe address
 }

}
