import data._
import scala.collection.mutable.{Map => MMap}

/**
  * Created by adam on 8/23/16.
  */
case class VendingMachine[T](items : Inventory[T]) {
  var balance : BigDecimal = 0
  def addToBalance(money : BigDecimal): Unit = {
    balance += money
  }
  def currentBalance() : BigDecimal = {
    balance
  }
  def withdrawBalance() : BigDecimal = {
    val amount = balance
    balance = 0
    amount
  }
  def buyProductWithBalance(index : T) : Either[String,Product] = {
    def buyItem(inventory : InventoryItem): Either[String,Product] = {
        if(balance >= inventory.price){
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
