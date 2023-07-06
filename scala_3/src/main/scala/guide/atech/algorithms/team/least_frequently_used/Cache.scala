package guide.atech.algorithms.team.least_frequently_used

trait Cache[K, V] {
  
  def put(key: K, value: V): Option[V]
  def get(key: K): Option[V]

}
