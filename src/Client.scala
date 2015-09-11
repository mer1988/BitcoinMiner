import akka.actor.{ActorSelection, Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer

/**
 * Created by Migue on 9/9/15.
 */


object Client {

  def main (args: Array[String]): Unit = {

    val remoteIP = "127.0.0.1"

    val clientSystem = ActorSystem("client", ConfigFactory.load(ConfigFactory.parseString("""
        akka {
            actor {
                provider = "akka.remote.RemoteActorRefProvider"
        }
        log-dead-letters = off
  } """)))
    val remotePath = "akka.tcp://BitcoinMinerSystem@" + remoteIP +":11111/user/master"

    val remoteServer = clientSystem.actorSelection(remotePath)

    val client1 = clientSystem.actorOf(Props(classOf[RemoteWorker], remoteServer), name = "client1")
    val client2 = clientSystem.actorOf(Props(classOf[RemoteWorker], remoteServer), name = "client2")
    val client3 = clientSystem.actorOf(Props(classOf[RemoteWorker], remoteServer), name = "client3")
    val client4 = clientSystem.actorOf(Props(classOf[RemoteWorker], remoteServer), name = "client4")

  }

}