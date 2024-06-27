/*
arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}


where (i + arr[i] >= k)
minStep[k] = min(minStep[k], miStep[k-i] + 1)

 */


val arr =  Array(1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9)
val cost = Array.fill(arr.length)(1000)
cost(0) = 0


(1 to arr.length-1) foreach( j =>
  {
    (j-1 to 0 by -1) foreach { k =>
      if (  j <= k + arr(k) ) {
        cost(j) = Math.min(cost(j), cost(k) + 1)
      }
    }
  })

cost.foreach(println)
