


/*

   2, 112, 12, 423, 3, 10




 */


import scala.util.control.Breaks._

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {

    val data2 = Array(0,0)

breakable {
  (0 to nums.length - 1) foreach { i =>
    (i + 1 to nums.length - 1) foreach { j =>
      if (target - nums(i) == nums(j)) {
        println(s"[$i,$j]");
        data2(0) = i
        data2(1) = j
        break
      }
    }
  }
}

    data2
  }

twoSum(Array(3,2,4), 6)