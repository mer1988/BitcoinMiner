import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory


/**
 * Created by Migue on 9/8/15.
 */
object Server{

	def main (args: Array[String]) {
		var numOfZeros = 3
				if(args.length<1)
					println("The default number of 0's of the bitcoin is "+numOfZeros)
					else{
						numOfZeros = args(0).toInt
								println("The number of 0's of the bitcoin is set to "+numOfZeros)
					}
		// Create an Akka system
		val system = ActorSystem("BitcoinMinerSystem",ConfigFactory.load(ConfigFactory.parseString("""
				akka {
				   actor {
				     provider = "akka.remote.RemoteActorRefProvider"
				   }
				   remote {
				     enabled-transports = ["akka.remote.netty.tcp"]
				     transport = "akka.remote.netty.NettyRemoteTransport"
				     netty.tcp {
				        hostname = "127.0.0.1"
				        port = 2552
				     }
				   }
				   log-dead-letters = off
				}
				""")))

				// create the master
				val master = system.actorOf(Props(new Master(4, 10000000, numOfZeros)), name = "master")

	}

}
