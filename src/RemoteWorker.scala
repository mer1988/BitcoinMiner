/**
 * Created by Migue on 9/10/15.
 */

import akka.actor._

class RemoteWorker(server: ActorSelection) extends Actor {

  //    def verifySHA(workload: List[String], numOfZeros: Int): List[String] = {
  //      var res = new ListBuffer[String]()
  //      var leadingZeros = ""
  //      for (i <- 1 to numOfZeros) {
  //        leadingZeros = leadingZeros + "0"
  //      }
  //      for (str ← workload) {
  //        val shaStr: String = SHA256.sha256(str)
  //        //println(shaStr)
  //        if (shaStr.startsWith(leadingZeros)) {
  //          res += shaStr
  //        }
  //
  //      }
  //      res.toList
  //    }
  server ! "hey!"

  def receive ={
    case str:String ⇒
      println(str)
      sender!"Hi, Back!"
  }
}