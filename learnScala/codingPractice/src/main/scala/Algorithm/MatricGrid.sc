

val metric: Array[Array[String]] = Array.ofDim(3, 3)


for(i <- 0 to 2){
  for(j <- 0 to 2){
    metric(i)(j) = List(i,j).mkString("(",",",")")//"(" + i + "," + j + ")"
  }
}

def printMatrix(matrix: Array[Array[String]]): Unit = {
  for(i <- 0 to 2){
    for(j <- 0 to 2){
      print(matrix(i)(j) + " ")
    }
    println()
  }
}

printMatrix(metric)