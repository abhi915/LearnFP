package recap

import recap.ThreadModelLimitation.runningThread

object ThreadModelLimitation {

  // 1. OOP encapsulation is only valid in the single-threaded world
  class BankAccount(private var amount: Int) {
    override def toString: String = s"${amount}"

    def withdraw(money: Int) = this.amount -= money

    def deposit(money: Int) = this.amount += money

    def getAmount = this.amount
  }

  val account = new BankAccount(2000)

  val depositThread = (1 to 1000).map(_ => new Thread( () => account.deposit(1)))
  val withdrawThread = (1 to 1000).map(_ => new Thread( () => account.withdraw(1)))

  /*
    - we don't knwo when threads are finished.
    - race condition

    solution synchronize the methods
      - race condition
      - deadlocks
   */

  // delegating task to thread
  var task: Runnable = null

  val runningThread: Thread = new Thread(() =>
  while(true) {
    while (task == null) {
      runningThread.synchronized {
        println("[background] waiting for a task...")
        runningThread.wait()
      }
    }


    task.synchronized {
      println("[background] I have a task")
      task.run()
      task = null
    }
  })

  def delegateToBackgroundThread(r: Runnable): Unit = {
    if (task == null) {
      task = r
      runningThread.synchronized {
        runningThread.notify()
      }
    }
  }


   def main(args: Array[String]): Unit = {
     /*

     Following code gives race condition. Because the deposit and withdraw methods are not synchronized.
     (depositThread ++ withdrawThread).foreach(_.start())
     Thread.sleep(1000)
      println(account.getAmount)

      */
     runningThread.start()
     Thread.sleep(1000)
      delegateToBackgroundThread(() => println("I am running in the background"))
     Thread.sleep(1000)
     delegateToBackgroundThread(() => println("I am running in the background again"))

  }

}
