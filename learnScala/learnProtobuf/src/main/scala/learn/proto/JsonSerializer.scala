package learn.proto

import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.Serialization.write
import org.json4s.native.JsonMethods._
import learn.proto.model.Address


trait JsonSerializer {

  implicit val formats: Formats = DefaultFormats

  implicit def AddressJsonSerializer: Serializer[Address] = (value: Address) => write(value).getBytes

  implicit def AddressJsonDeSerializer: Deserializer[Address] = (value: Array[Byte]) => parse(new String(value)).extract[Address]

}

object JsonSerializer extends JsonSerializer