import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

object ApiRoutes {
  // Request and response models
  final case class Container(id: String, location: String)
  final case class Rules(distanceMatrix: Vector[Vector[Int]], priority: Vector[Int])
  final case class OptimizationInput(containers: Vector[Container], rules: Rules)
  final case class Move(containerId: String, from: String, to: String, cost: Int)
  final case class OptimizationResult(moves: Vector[Move], totalCost: Int)

  // JSON formats
  implicit val containerFormat = jsonFormat2(Container)
  implicit val rulesFormat = jsonFormat2(Rules)
  implicit val inputFormat = jsonFormat2(OptimizationInput)
  implicit val moveFormat = jsonFormat4(Move)
  implicit val resultFormat = jsonFormat2(OptimizationResult)

  val route =
    path("optimize") {
      post {
        entity(as[OptimizationInput]) { input =>
          val result = Optimizer.optimize(input)
          complete(result)
        }
      }
    }
}
