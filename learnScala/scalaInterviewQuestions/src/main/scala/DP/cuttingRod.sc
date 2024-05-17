/*
  1 2 3 4  5   6  7   8
  1 5 8 9 10   17 17  20



  1   2       3
  1   5    max(combination of 3)


  combination of 3 = 0 to k => i = max[(i) + (k-i)]

v(5)= n(4) + n(0)
      n(3) + n(1)
      n[i] +
      [n- (n-1)][
      n(1)
      n(1) + n(4)

 */

val n: Int = 8
val givenData = Array(3 ,5, 8, 9, 10,17, 17, 20)

def findOptimumValue(n: Int, value: Array[Int]) = {
  if (n > 0) {
    (0 to n - 1) foreach { i =>
      value(n) = Math.max(value(n), value(n - (i + 1)) + value(i))
    }
  }
}

def getOptimumArray(n: Int, value: Array[Int]) = {
  (1 to n-1) foreach {i =>
    findOptimumValue(i, value)
  }
  value
}

getOptimumArray(8, givenData).foreach(println)
