import fs2.Stream
import fs2.Pure

/*
  apply
  emit
  emits
  iterate
  unfold
 */
val s: Stream[Pure,Int] = Stream(1,2,3)
val s2: Stream[Pure,Int] = Stream.empty

val s3 = Stream.emit(42)
val s4 = Stream.emits(List(1,2,3))

val s5 = Stream.iterate(1)(_ + 1)
val s6 = Stream.unfold(1)(s => Some((s, s + 1)))

s.toList

s5.take(5).toList

s6.take(5).toList

val s7 = Stream.unfold(97)(s => if (s >= 97 + 26) None else Some(s.toChar, s+1))

s7.toList