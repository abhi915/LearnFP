/*
1. Validation
2. Dependency Injection
3. Tracking
4. State Management
5. Stack Safety
6. Laziness and Other evaluation mode
7. Monad transformer
8. Side effect suspension
 */

import cats._
import cats.implicits._
import cats.data._

5.valid[NonEmptyList[String]]
"error".invalid[Int]

5.invalidNel[String]
"error".validNel[Int]

5.validNec[String]
"error".invalidNec[Int]

6.validNec[String].ensure(NonEmptyChain("value iss not even"))(_%2 == 0)

Validated.cond(true, 6, "error")