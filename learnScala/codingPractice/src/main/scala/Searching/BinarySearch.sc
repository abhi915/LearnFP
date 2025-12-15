import scala.annotation.tailrec

val data = List(5,4,19,8,2,3)

val sortedData = data.sorted




@tailrec
def binarySearch(start: Int, end: Int, value: Int): Int = {
  if(start > end) return -(start + 1);
  else {
     val mid = start + (end-start)/2;
     if(sortedData(mid) == value) return mid;
     else if(sortedData(mid) < value)  {
       return binarySearch(mid+1, end, value)
     } else {
       return binarySearch(start, mid-1, value)
     }
  }
}

binarySearch(0, sortedData.length-1, -10)




// quick sort
//  last index element =
//

val arr = Array(5, 11, 3, 1, 36, 4)




val x = arr(arr.length-1)

var si = 0;
for(i <- 0 until arr.length-1) {
    if(arr(i) < x) {
      val temp = arr(si)
      arr(si) = arr(i)
      arr(i) = temp
      si = si+1
    }
}

arr(arr.length-1) = arr(si)
arr(si) = x

arr.toList








// merge sort




