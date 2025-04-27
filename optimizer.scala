object Optimizer {
  import ApiRoutes._

  def optimize(input: OptimizationInput): OptimizationResult = {
    // Mock strategy: move second container to first's location with the given cost
    if (input.containers.length < 2) {
      return OptimizationResult(Vector(), 0)
    }

    val from = input.containers(1).location
    val to = input.containers(0).location
    val cost = input.rules.distanceMatrix(1)(0)

    val move = Move(input.containers(1).id, from, to, cost)

    OptimizationResult(Vector(move), cost)
  }
}
