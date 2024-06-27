/*

s1 = "ABCDE"
s2=  "AXCXBYE"

if x[i] == y[j]
   a[i-1][j-1] + 1
if x[i]  != y[j]
    max (a[i-1][j], a[i][j-1])

max( a[i-1][j-1] + 1,  a[i-1][j], a[i][j-1] )

    0  A  B  C  D  E
 0  0  0  0  0  0  0
 A  0  1  1  1  1  1
 X  0  1  1  1  1  1
 C  0  1  1  2  2  2
 B  0  1  2  2  2  2
 */