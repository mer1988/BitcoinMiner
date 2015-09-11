import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory


/**
 * Created by Migue on 9/8/15.
 */
object Server{

  def main (args: Array[String]) {

    // Create an Akka system
    val system = ActorSystem("BitcoinMinerSystem", ConfigFactory.load(ConfigFactory.parseString("""
        akka {
          actor {
            provider = "akka.remote.RemoteActorRefProvider"
          }
          remote {
            enabled-transports = ["akka.remote.netty.tcp"]
            transport = "akka.remote.netty.NettyRemoteTransport"
            netty.tcp {
              hostname = "127.0.0.1"
              port = 11111
            }
          }
          log-dead-letters = off }""")))


    // create the master
    val master = system.actorOf(Props(new Master(4, 10000000, 3)), name = "master")

    // start the calculation
    master ! Start

  }

}
