/*
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */

import scala.collection.immutable.Set



  def calculateMaxSize(currentSize: Int, maxSize: Int, char: Seq[Char], arrOccurence: Set[Char] ): Int =
    if (char.isEmpty) maxSize
    else if (!arrOccurence.contains(char.head)) {
      calculateMaxSize(currentSize + 1, math.max(currentSize + 1, maxSize), char.tail, arrOccurence + (char.head+1))
    } else {
      calculateMaxSize(1, maxSize, char.tail, arrOccurence)
    }


  def lengthOfLongestSubstring(s: String): Int = {
    calculateMaxSize(0, 0, s.toSeq, Map[Char]())
  }

lengthOfLongestSubstring("abcabcbb")