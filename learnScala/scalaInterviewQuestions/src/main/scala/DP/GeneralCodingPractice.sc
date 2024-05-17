


// Example of higher order function
def squarue = (x: Int) => x*x
def cube    = (x: Int) => x*x*x

def calculate(f: Int =>Int)(x: Int) = f(x)


def calculateSquare = calculate(squarue) _
def calculateCube = calculate(cube) _

println(calculateSquare(5))
println(calculateCube(5))

// Example of future in scala

import scala.collection.immutable.Stream
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure


def calculateTable(x: Int) : Future[List[Int]] = Future {
    (1 to 10).map(_ * x).toList
}

val tableOf2 = calculateTable(2)

tableOf2.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
}


// Example of implicits and ADT
trait Shape

case class Circle(radius: Int) extends Shape
case class Rectangle(height: Int, width: Int) extends Shape
case class Square(side: Int) extends Shape

trait Area[Shape] {
  def area(x: Shape): Double
}

object Area {
  implicit val calculateCircleArea: Area[Circle]  = (x: Circle) => 3.14*x.radius*x.radius
  implicit val calculateSquareArea: Area[Square] = (x: Square) => x.side*x.side
  implicit val calculateRactangle: Area[Rectangle] = (x: Rectangle) => x.width*x.height
}

def calculateAreaOf[Shape](x: Shape)(implicit instance: Area[Shape])  = instance.area(x)

calculateAreaOf(Circle(2))
calculateAreaOf(Square(2))
calculateAreaOf(Rectangle(1,2))

// sorting: sorted, sortBy, sortWith

val listData = List(1,3,2, 6, 5)
listData.sorted
listData.sortWith( (x: Int, y: Int) => x>y)


// partial function
val function: PartialFunction[Int,  String] = new PartialFunction[Int, String] {
  override  def isDefinedAt(x: Int): Boolean = x % 2 == 0

  override def apply(v1: Int): String =  s"${v1} is even"
}


val dataOption = Some(2)
val dataOption1 = Some(1)

dataOption.collect(function)
dataOption1.collect(function)

val list = List(1,2,3,4)
function.lift(1)
function.lift(2)

// fib series
// palindrome -- aba


val data = "abca"
val reverse = data.reverse

val n = if (data.length %2 == 0) data.length/2 else data.length/2 + 1

 s"data is palindrome: ${data.equals(reverse)}"

//scala program to find smallest and 2nd smallest no
val data = Array(1,2,3,94,5,0,-1)

data.foldLeft( (132: Int, 133: Int))((acc,data) => {
  if (data < acc._1) {
    (data, acc._1)
  } else if (data < acc._2) {
    (acc._1, data)
  }else {
    acc
  }
})


// Write a word count program without using Spark.
val dataList = List("abc","xyz","hello123","abhishek","gopinath","patil")
dataList.map( data =>
  (data, data.length)
)

// Write a program to find Number of Values which are perfect square?
// 144 -> 2 -> 72



def findRoot(number: Int, root:List[Int]): Boolean = {
  if (number == 1)  true
  else if (number < 1)  false
  else {
    val data: Seq[Boolean] = (2 to number / 2 + 1).map {
      devisor =>
        if (number % devisor == 0) findRoot(number / devisor, root :+ devisor)
        else findRoot(-1, root)
    }
    data.reduce(_ || _)
  }
}

findRoot(196, List.empty[Int])

// Write a program to find smallest and largest kth elements in List
