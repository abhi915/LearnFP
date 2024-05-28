
/*
 scala Equiv class
 scala.math.Equiv
 */

import scala.math.{Equiv, abs}

val id = 10L
val id1 = 11L

// universal
val eq1 = Equiv.universal[Long]
val check = eq1.equiv(id, id1)

//from function
val eqFromFunction = Equiv.fromFunction[Long]( (id1,id2) => abs(id1) == abs(id2))
eqFromFunction.equiv(10L,-10L)

//for by need implicits
implicit val implicitLong = Equiv.universal[Long]
val id2 = 10L
val z = Equiv.by((id :Long) => id)
z.equiv(10L, 10L)




/*
cats
Simple typeclasses
1. Eq
2. Order
3. Show
4. Monoid

Eq
  - reflexivityEq = x <-> x
  - symmetryEq(x,y) x = y iff y = x
  - transitivity x = y and y = z then x = z

 */

// cats Eq
import cats._
import cats.implicits._

case class Account(id: Long, number: String, balance: Double, owner: String)

object Account {
  // default instance
  implicit val eqByAccount: Eq[Account] = Eq.fromUniversalEquals
  object Instance {
    // instance we want to add  in scope
    implicit def byIdEq(implicit eqLong: Eq[Long]): Eq[Account] = Eq.instance[Account]((acc1,acc2) => eqLong.eqv(acc1.id, acc2.id))
    implicit def byIdEq2(implicit eqLong: Eq[Long]): Eq[Account] = Eq.by(_.id)
    // compare accoundbyNumber
    implicit def byNumberEq(implicit eqString: Eq[String]): Eq[Account] = Eq.by(_.number)
  }

}

val account1 = Account(1, "1", 1000, "A")
val account2 = Account(2, "1", 1500, "B")

// Universal instance
Eq[Account].eqv(account1, account2)

// Use directly
Account.Instance.byIdEq.eqv(account1, account2)
Account.Instance.byNumberEq.eqv(account1, account2)

// companion object instance
account1 === account2

import Account.Instance.byNumberEq
account1 === account2


/*
scala ordering, order
 -- scala.math.Ordering
   trait Ordering[T] extends Comparator[T] with PartialOrdering[T] with Serializable
   -- when required order based on more than 1 state

 -- scala.math.Ordered
   trait Ordered[A] extends Comparable[A]
   -- When required ordered based on only 1 state
 */

// ordering

import scala.util.Sorting
val pairs = Array(("a", 5, 2), ("c", 3, 1), ("b", 1, 3))

// sort by 2nd element
Sorting.quickSort(pairs)(Ordering.by[(String,Int,Int), Int](_._2))
pairs.map(x => print(x.toString()))
println

// sort by 1st and then 3rd element
Sorting.quickSort(pairs)(Ordering[(Int, String)].on(x => (x._3,x._1)))
pairs.map(x => print(x.toString()))


// Order in cats

case class Account1(id: Long, number: String, balance: Double, owner: String)

object Account1 {
  // default instance
  implicit def orderById(implicit orderLong: Order[Long]): Order[Account] = Order.from((acc1,acc2) => orderLong.compare(acc1.id,acc2.id) )

  object Instance {
    implicit def orderById2(implicit orderLong: Order[Long]):Order[Account] = Order.by(_.id)
  }

  def sort(accList: List[Account1])(implicit orderA: Order[Account1]) = {
    accList.sorted(orderA.toOrdering)
  }
}





// Show
case class Account2(id: Long, number: String, balance: Double, owner: String)

object Account2 {
  // default instance
  implicit def universalShow: Show[Account2] = Show.fromToString

  object Instance {
    implicit val pecularShow: Show[Account2] = Show.show(acct2 => {
      s"show id ${acct2.id} and balance ${acct2.balance}"
    })
  }

}



