/**
 * Created by Migue on 9/9/15.
 */

import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

class Master(nrOfWorkers: Int, numOfMessages: Int, numOfZeros :Int) extends Actor {
  var joined: Int = 0
  var found: Int = 0
  var processed : Int = 0
  var generated: Int = 0
  var workload: Int = 5
  var prefix : String = "miguelrodriguez"
  var clients = ArrayBuffer[ActorRef]()

  val sw1 = context.actorOf(Props[ServerWorker], name = "sw1")
    sw1 ! Start
  val sw2 = context.actorOf(Props[ServerWorker], name = "sw2")
    sw2 ! Start
  val sw3 = context.actorOf(Props[ServerWorker], name = "sw3")
    sw3 ! Start
  val sw4 = context.actorOf(Props[ServerWorker], name = "sw4")
    sw4 ! Start



  def receive = {
    case Ready(client) =>
      println("Someone is ready!")
      
      if (generated < numOfMessages){
        clients.append(client)
        joined +=1
        var messages = new ListBuffer[String]()
        for (j <- 1 to workload){
          messages += prefix + randomString(20)
        }
        generated += workload
        sender! Work(messages.toList, numOfZeros)
      }
      else sender ! Stop
      
    case Result(coins, strings) ⇒
      
      processed += workload
      if (coins.length > 0){
        found += coins.length
        for(i <- 0 until coins.length)
          println(coins(i)+"  "+strings(i))
      }

      if (generated < numOfMessages){
        var messages = new ListBuffer[String]()
        for (j <- 1 to workload){
          messages += prefix + randomString(20)
        }
        generated += workload
        sender! Work(messages.toList, numOfZeros)
      }
      else 

      if (processed == numOfMessages) {

        for (i <- 0 until clients.length){
          clients(i) ! Stop
        }

        println("Find " + found + " bitcoins")
        println(joined+" actors joined")
        context.stop(self)
        context.system.shutdown()
      }
      
      case str: String ⇒
        println(str)
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
