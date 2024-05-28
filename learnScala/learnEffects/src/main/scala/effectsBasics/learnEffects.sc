import c

case class MyIO[A](unsafeRun: () => A) {
  def map[B](f: A => B): MyIO[B] = MyIO(() => f(unsafeRun()))
  def flatMap[B](f: A => MyIO[B]): MyIO[B] = MyIO(() => f(unsafeRun()).unsafeRun())
}




val io = MyIO(() => {
  println("Running IO")
  42
})

val io2 = io.map(_ + 1)
val io3 = io2.flatMap(x => MyIO(() => x + 1)).flatMap(x => MyIO(() => x + 1))

io2.unsafeRun()

io3.unsafeRun()


val localTime = MyIO(() => System.currentTimeMillis())
def measure[A](computation: MyIO[A]): MyIO[Long] = {
  for {
    start <- localTime
    _  <- computation
    end <- localTime
  } yield end - start
}

val measured = measure(MyIO(() => Thread.sleep(1000)))

measured.unsafeRun()



