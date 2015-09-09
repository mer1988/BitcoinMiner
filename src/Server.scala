import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import akka.routing.RoundRobinRouter


/**
 * Created by Migue on 9/8/15.
 */
object Server{

  def main (args: Array[String]) {

    // Create an Akka system
    val system = ActorSystem("BitcoinMinerSystem")


    // create the master
    val master = system.actorOf(Props(new Master(4, 1000000, 3)), name = "master")

    // start the calculation
    master ! Start

  }

}
