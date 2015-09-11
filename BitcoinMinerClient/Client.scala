import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
 * Created by Migue on 9/9/15.
 */


object Client {

  def main (args: Array[String]): Unit = {
    var remoteIP = "127.0.0.1"
    
    if(args.length<1)
      println("The default remote IP address is 127.0.0.1")
    else{
      remoteIP=args(0)
      println("The remote IP address is set to "+remoteIP)
    }
    

    val clientSystem = ActorSystem("client", ConfigFactory.load(ConfigFactory.parseString("""
        akka {
          actor {
             provider = "akka.remote.RemoteActorRefProvider"
          }
          log-dead-letters = off
        }
     """)))
     
    val remotePath = "akka.tcp://BitcoinMinerSystem@" + remoteIP +":5678/user/master"
    val clientMaster = clientSystem.actorOf(Props(new ClientMaster(remotePath)), name = "LocalActor")  // the local actor

  }

}
