/*

set[] = {3, 34, 4, 12, 5, 2}, sum = 9
 */


val data = List(3, 34, 4, 12, 5, 2)
val sum = 9
data.dropRight(1)
data.last

def isSubSetAvailable(set: List[Int], sum: Int): Boolean = {
  if (sum == 0)  true
  else if (sum < 0 || set.length ==0) false
  else {
    isSubSetAvailable(set.dropRight(1), sum) || isSubSetAvailable(set.dropRight(1), sum - set.last)
  }
}

isSubSetAvailable(data, sum)