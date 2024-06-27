/*

    0  a b c d e f g h i
  0 0  0 0 0 0 0 0 0 0 0
  a 0  1 0 0 0 0 0 0 0 0
  x 0  0 0 0
  b 0  0 1
  c 0
  d 0
  y 0


  a[i] == a[j]
     1 + a[i-1][j-1]

  max(a[i][j])


 */


val x = "GeeksforGeeks"
val y = "GeeksQuiz"

val arr= Array.ofDim[Int](x.length + 1, y.length+1)


1 to x.length foreach {i =>
  1 to y.length foreach { j =>
      if (x(i-1) == y(j-1)) {
        arr(i)(j) = math.max(arr(i)(j), arr(i-1)(j-1)+1)
      }
  }
}

1 to x.length foreach{ i =>
  println(arr(i).mkString("(",",",")"))
}




