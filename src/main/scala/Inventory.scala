import data._
import scala.collection.mutable.{Map => MMap}

/**
  * Created by adam on 8/23/16.
  */
case class Inventory[T](items: MMap[T, InventoryItem]) {

  def get(index: T): Option[InventoryItem] = {
    items.get(index)
  }

  def take(index: T): Either[String, Product] = {
    get(index) match {
      case Some(inventoryItem) =>
        if (inventoryItem.quantity > 0) {
          items(index) = InventoryItem(inventoryItem.product, inventoryItem.price, inventoryItem.quantity - 1)
          Right(inventoryItem.product)
        } else {
          Left("Product Out Of Stock")
        }
      case None => Left("Product does not exist")
    }
  }
}
