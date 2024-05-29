package learn.proto
import learn.proto.models.pb._

/*
* manual transformation is added with chimney, we added transformation using library
* */

object PbConverters {

  def from(e: Address): output.Address =
    output.Address(
      postbox = e.postbox,
      street = e.street,
      city = e.city
    )

  def to(e: output.Address): Address =
    Address(
      postbox = e.postbox,
      street = e.street,
      city = e.city
    )

}
