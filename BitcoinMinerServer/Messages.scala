/**
 * Created by Migue on 9/9/15.
 */
import akka.actor.ActorRef

case class  Result(coins : List[String], strings : List[String])
case class  Work(workload : List[String], numOfZeros: Int)
case class  Ready(client: ActorRef)
case object Start
case object Stop
