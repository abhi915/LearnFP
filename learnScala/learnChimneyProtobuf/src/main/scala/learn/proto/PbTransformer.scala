package learn.proto

import io.scalaland.chimney.PartialTransformer
import learn.proto.models.pb._
import io.scalaland.chimney.{PartialTransformer, Transformer}
import scalapb.UnknownFieldSet


trait PbTransformer extends Serializable {

  implicit def toPbAddress: PartialTransformer[Address, output.Address] =
    Transformer.definePartial[Address, output.Address]
      .withFieldConst(_.unknownFields, UnknownFieldSet.empty)
      .buildTransformer

  implicit def fromPbAddress: PartialTransformer[output.Address, Address] =
    Transformer.definePartial[output.Address, Address]
      .buildTransformer

}

