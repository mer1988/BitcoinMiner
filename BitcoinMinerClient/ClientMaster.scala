

/**
 * @author fiona
 */
import akka.actor.{ActorSelection, Props, ActorSystem, Actor}

class ClientMaster(remotePath: String) extends Actor{
    var numOfClientActor=4
    val remoteServer = context.actorSelection(remotePath)
    val client1 = context.actorOf(Props(new RemoteWorker(remoteServer, self)), name = "client1")
    val client2 = context.actorOf(Props(new RemoteWorker(remoteServer, self)), name = "client2")
    val client3 = context.actorOf(Props(new RemoteWorker(remoteServer, self)), name = "client3")
    val client4 = context.actorOf(Props(new RemoteWorker(remoteServer, self)), name = "client4")

    def receive ={
      case Stop =>
        numOfClientActor -= 1
        if(numOfClientActor==0)
        {
          context.stop(self)
          context.system.shutdown()
        }
      case s:String =>
        println(s)
    }
    
    
}