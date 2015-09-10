/**
 * Created by Migue on 9/9/15.
 */
import akka.actor.ActorRef

case class Result(coins : List[String])
case class  Work(workload : List[String], numOfZeros: Int)
case object Start
