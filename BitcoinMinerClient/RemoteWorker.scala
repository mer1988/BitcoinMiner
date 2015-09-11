/**
 * Created by Migue on 9/10/15.
 */

import akka.actor._

import scala.collection.mutable.ListBuffer

class RemoteWorker(server: ActorSelection, climaster :ActorRef) extends Actor {
  server ! Ready(self)

  def receive ={
    case Work(workload, numOfZeros) ⇒

      var res = new ListBuffer[String]()
      var strs = new ListBuffer[String]()

      var leadingZeros = ""
      for (i <- 1 to numOfZeros){
        leadingZeros = leadingZeros+"0"
      }
      for (str ← workload){
        val shaStr : String = SHA256.sha256(str)
        //println(shaStr)
        if (shaStr.startsWith(leadingZeros)){
          res+=shaStr
          strs += str
        }

      }
      sender ! Result(res.toList, strs.toList)

   case Stop =>
      climaster ! Stop
      context.stop(self)
   
   case s:String =>
      println(s)
  }
}