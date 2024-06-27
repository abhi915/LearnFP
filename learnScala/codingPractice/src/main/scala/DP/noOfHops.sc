/*

 --->
 |
 |

0 1
1 1



 */

val n = 10

val arr = Array.ofDim[Int](11)

arr(0) = 0
arr(1) = 1
arr(2) = 2
arr(3) = 4


(4 to n) foreach { i =>
  arr(i) = arr(i-1) + arr(i-2) + arr(i-3)
}

println(arr.mkString("(",",",")"))