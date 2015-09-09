/**
 * Created by Migue on 9/9/15.
 */

import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import akka.routing.RoundRobinRouter

import scala.collection.mutable.ListBuffer

class Master(nrOfWorkers: Int, nrOfMessages: Int, numOfZeros :Int) extends Actor {

  var found: Int = 0
  var processed : Int = 0
  var coins_list = new ListBuffer[String]()

  var workload: Int = 1

  var prefix : String = "mer"



  val workerRouter = context.actorOf(
    Props[ServerWorker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")

  def receive = {
    case Start ⇒
      for (i ← 0 until nrOfMessages by workload){
        var messages = new ListBuffer[String]()

        for (j <- 1 to workload){
          messages += prefix + randomString(20)
        }

        workerRouter ! Work(messages.toList, numOfZeros)
      }

    case Result(coins) ⇒
      found += coins.length
      processed += workload
      for (coin <- coins){
        coins_list += coin
      }

      if (processed == nrOfMessages) {




        for (coin <- coins_list){
          println(coin)
        }

        println(found)
        context.stop(self)
        context.system.shutdown()
      }
  }

  def randomString(length: Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }

}
