
/*
sum = 10, coins[] = {2, 5, 3, 6}


                              without using current coint + using current coin
no of possibilities a(i)(j) = a(i-1)(j)                   + a(i)(j-coins(j))

sum = 10

   0  1  2  3  4  5...  n
0  1  0  0  0  0
2  1  0  1  0  1  0
5  1  0  1  0  1  1
3  1  0  1   1
6
*/


val arrCoins =List(2,5,3,6)



// without using current coin and using current coin

def findCombination(arrCoin: List[Int])(sum: Int): Int = {
  if (sum == 0) 1
  else if (sum < 0 || (arrCoin.length == 0)) 0
  else {
      findCombination(arrCoin)(sum-arrCoin(arrCoin.length-1)) + findCombination(arrCoin.take(arrCoin.length-1))(sum)
  }
}

val data = findCombination(arrCoins)(10)
println(data)




