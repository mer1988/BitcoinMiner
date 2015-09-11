/**
 * Created by Migue on 9/9/15.
 */

import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import akka.routing.{RoundRobinGroup, RoundRobinRouter}

import scala.collection.mutable.ListBuffer

class Master(nrOfWorkers: Int, numOfMessages: Int, numOfZeros :Int) extends Actor {
  var joined: Int = 0



  var found: Int = 0
  var processed : Int = 0
  var generated: Int = 0

  var coins_list = new ListBuffer[String]()
  var str_list = new ListBuffer[String]()

  var workload: Int = 5
  var prefix : String = "miguelrodriguez"




  val sw1 = context.actorOf(Props(classOf[ServerWorker]), name = "sw1")
    sw1 ! Start
  val sw2 = context.actorOf(Props(classOf[ServerWorker]), name = "sw2")
    sw2 ! Start
  val sw3 = context.actorOf(Props(classOf[ServerWorker]), name = "sw3")
    sw3 ! Start
  val sw4 = context.actorOf(Props(classOf[ServerWorker]), name = "sw4")
    sw4 ! Start



  def receive = {
//    case Start ⇒
//      for (i ← 0 until nrOfMessages by workload){
//
//
//        for (j <- 1 to workload){
//          messages += prefix + randomString(20)
//        }
//
//        workerRouter ! Work(messages.toList, numOfZeros)
//      }
    case Ready =>
      println("Someone is ready!")
      if (generated < numOfMessages){
        var messages = new ListBuffer[String]()
        for (j <- 1 to workload){
          messages += prefix + randomString(20)
        }
        generated += workload
        sender! Work(messages.toList, numOfZeros)
      }
      else{
        sender ! "No more work to do"
      }


    case str: String ⇒
      println(str)


    case Result(coins, strings) ⇒

      found += coins.length
      processed += workload
      //println(processed)
      for (coin <- coins){
        coins_list += coin
      }

      for (str <- strings){
        str_list += str
      }

      if (generated < numOfMessages){
        var messages = new ListBuffer[String]()
        for (j <- 1 to workload){
          messages += prefix + randomString(20)
        }
        generated += workload
        sender! Work(messages.toList, numOfZeros)
      }

      if (processed == numOfMessages) {

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
