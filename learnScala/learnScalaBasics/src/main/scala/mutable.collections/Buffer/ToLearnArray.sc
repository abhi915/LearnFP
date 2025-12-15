
val y: Array[Array[Int]] = Array.ofDim[Int](3,3)


var count = 1;
(0 until 3) foreach  { i =>
  (0 until 3) foreach  { j =>
    y(i)(j) =  count;
    count = count + 1;
  }
}

y.toList




