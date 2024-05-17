// C_0=C_1=1 \ and \ C_{n}=\sum_{i=0}^{n-1}C_iC_{n-i-1} \ for \ n\geq 2

/*
C(0)=1
C(1)=1
C(2) = C(0)(1) + C(1)C(0) = 2
C(3)= C(0)C(2) + C(1)C(1) + C(2)C(0)

 */



val n=7
var c = new Array[Int](n).padTo(n,0)
c(0) = 1
c(1) = 1
(2 to n-1 ) foreach { i =>
  (0 to i-1) foreach { j =>
    c(i) = c(i) + c(j)*c(i-j-1)
  }
}

c.foreach(println)