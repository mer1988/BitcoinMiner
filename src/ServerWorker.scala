import java.security.{MessageDigest}
import akka.actor.Actor
import scala.collection.mutable.ListBuffer

/**
 * Created by Migue on 9/8/15.
 */
class ServerWorker extends Actor {

  def verifySHA(workload: List[String], numOfZeros: Int): List[String] = {
    var res = new ListBuffer[String]()
    var leadingZeros = ""
    for (i <- 1 to numOfZeros){
      leadingZeros = leadingZeros+"0"
    }
    for (str ← workload){
      val shaStr : String = SHA256.sha256(str)
      //println(shaStr)
      if (shaStr.startsWith(leadingZeros)){
          res+=shaStr
      }

    }
    res.toList
  }


  def receive = {
    case Work(workload, numOfZeros) ⇒
      sender ! Result(verifySHA(workload, numOfZeros)) // perform the work
  }
}