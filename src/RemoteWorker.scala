import akka.actor.{ActorSelection, Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
 * Created by Migue on 9/9/15.
 */


object RemoteWorker {

  def main (args: Array[String]): Unit = {

    val remoteIP = "127.0.0.1"

    val clientSystem = ActorSystem("client", ConfigFactory.load(ConfigFactory.parseString("""
        akka {
            actor {
                provider = "akka.remote.RemoteActorRefProvider"
        }
        log-dead-letters = off
  } """)))
    val remotePath = "akka.tcp://BitcoinMinerSystemg@" + remoteIP +":11111/user/master"

    val remoteServer = clientSystem.actorSelection(remotePath)

    val client1 = clientSystem.actorOf(Props(classOf[ClientWorker], remoteServer), name = "client1")

  }

  class ClientWorker (server: ActorSelection) extends Actor {

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

    def receive = {
      case str:String ⇒
        println(str)
        sender ! "Hi, Back!"
    }

}



}