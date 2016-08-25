import scala.collection.SortedMap
import scala.collection.mutable.{ListBuffer => MList, Map => MMap}

/*
Change sorts the vending machines money as a sorted map for ease of change,
yet takes input as list of coins for easy of callings
 */
case class ChangeDispenser(coins: List[Int]) {
  var coinsMap = makeSortedMap(coins)

  def addCoins(money: List[Int]) = {
    coinsMap = coinsMap ++ makeSortedMap(money)
  }

  //make change uses a greedy algo to choose change
  //from largest to smallest, by looping through a sorted map
  def makeChange(amount: Int): List[Int] = {
    var changeleft = amount
    var change = List[Int]()
    for ((key, value) <- coinsMap) {
      val count = Math.min(changeleft / key, value)
      if (changeleft > 0 && count > 0) {
        changeleft = changeleft - (count * key)
        coinsMap = coinsMap + (key -> (value - count))
        change = change ++ List.fill(count)(key)
      }
    }
    change
  }

  private def makeSortedMap(list: List[Int]): SortedMap[Int, Int] = {
    val grouped = list.groupBy(identity).mapValues(_.size).toList
    SortedMap(grouped : _*)(implicitly[Ordering[Int]].reverse)
  }
}