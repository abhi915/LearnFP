import java.util
import scala.collection.mutable.ArrayBuffer

/*
C -> A
A -> B
A -> D
A -> E
D -> C
E -> B
E -> D


C : A
A : B, D, E
D : C
E : B, D




iterate over vertex of graph
if vertex not present create vertex and add edge in transpose graph
if vertex present add edge in transpose graph


       A -> 0
       B -> 1



      0  1  2  3  4
   0  0  1  0   1  1
   1  0  0  0   0  0
   2  1  0  0   0  0
   3  0  0  1   0  0
   4  0  1  0   1  0





 */




