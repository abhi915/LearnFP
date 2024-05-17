import cats.effect.kernel.Resource.Pure
import fs2.Stream

import scala.Option
import fs2.Pure



val s1 = Stream(1, 2, 3)
val s2 = Stream(4, 5, 6)


(s1++s2).toList

