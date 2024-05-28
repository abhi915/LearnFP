package learn.proto

package object model {

  case class Address(postbox: Int, street: Option[String], city: String)

}
