package learn

package object proto {

  case class Address(postbox: Int, street: Option[String], city: String)

}
