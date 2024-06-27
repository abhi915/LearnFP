/*



 */


def mergeSort( lower: Int,  high: Int, medium: Int, arr: Array[Int]) = ???


def sort(arr: Array[Int], l: Int, h:Int): Unit = {
  if (l >= h)  {  () } else {
    val m = (l + h)/2
    sort(arr, l, m)
    sort(arr, m+1, h)
    mergeSort(l,h,m, arr)
  }
}