import cats._
import cats.implicits._
import cats.data._

val singReader: Reader[Int, String] = Reader(n => if (n > 0) "natural numbers" else "not a natural number")
singReader.run(5)

val parityReader: Reader[Int, String] = Reader(n => if(n%2 == 0) "even" else "odd")
parityReader.run(6)

val discriptReader: Reader[Int, String] = for {
  sign <- singReader
  parity <- parityReader
} yield s"${sign} and  ${parity}"

discriptReader(5)

val addOneReader: Reader[Int, Int] = for {
  eval <- Reader((x: Int) => x+1)
} yield eval

val addOneReader1: Reader[Int, Int] = for {
  eval <- Reader(identity[Int])
} yield eval + 1


case class Person(id: Long, name: String)
case class Account(id: Long, ownerId: Long)

trait AccountRepository {
  val acctRep: Service

  trait Service {
    def findByAccountId(id: Long): Account
  }
}

trait PersonRepository {
  val personRep: Service

  trait Service {
    def findPersonById(id: Long): Person
  }
}

trait LiveAccount extends AccountRepository {
  override val acctRep: Service = new Service {
    override def findByAccountId(id: Long) = Account(id, 2)
  }
}

trait LivePerson extends PersonRepository {
  override val personRep: Service = new Service {
    override def findPersonById(id: Long) = Person(2, "abhi")
  }
}


def findNextAccount(id: Long): Reader[AccountRepository, Account] =
  for {
    accountModule <- Reader(identity[AccountRepository])
    acc = accountModule.acctRep.findByAccountId(id + 1)
  } yield acc


def findOwnerOfAccount(id: Long) =
  for {
    accountModule <- Reader(identity[AccountRepository])
    personeModule <- Reader(identity[PersonRepository])
    acct = accountModule.acctRep.findByAccountId(id)
    owner = personeModule.personRep.findPersonById(acct.id)
  } yield owner.name


type env = AccountRepository with PersonRepository
val liveEnv : env =  new LiveAccount with LivePerson

findOwnerOfAccount(4).run(liveEnv)
