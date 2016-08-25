import data._

import scala.collection.mutable.{ListBuffer => MList, Map => MMap}

case class VendingMachine[T](items: Inventory[T], coins: List[Int]) {
  val change = ChangeDispenser(coins)
  var balance: Int = 0

  def addToBalance(money: List[Int]): Unit = {
    balance += money.sum
    change.addCoins(money)
  }

  def currentBalance(): Int = {
    balance
  }

  def withdrawBalance(): List[Int] = {
    val amount = balance
    balance = 0
    change.makeChange(amount)
  }

  def buyProductWithBalance(index: T): Either[String, Product] = {
    def buyItem(inventory: InventoryItem): Either[String, Product] = {
      if (balance >= inventory.price) {
        val product = items.take(index)
        balance -= inventory.price
        product
      } else {
        Left("Insufficent Funds")
      }
    }
    items.get(index) match {
      case Some(inventory) => buyItem(inventory)
      case None => Left("Product does not exist")
    }
  }
}