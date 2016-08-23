//import Currencies.Currency
import data._

import scala.collection.mutable.{Map => MMap}

/**
  * Created by adam on 8/23/16.
  */
case class Inventory(items : MMap[(Char,Int),InventoryItem]){

 def get(row : Char, column : Int): Option[InventoryItem] = {
   items.get((row,column))
 }

 def take(row: Char, column : Int): Either[String, Product] = {
   get(row,column) match {
     case Some(inventoryItem) =>
       if(inventoryItem.quantity > 0) {
         items((row, column)) = InventoryItem(inventoryItem.product, inventoryItem.price, inventoryItem.quantity - 1)
         Right(inventoryItem.product)
       } else {
         Left("Product Out Of Stock")
       }
     case None =>  Left("Product does not exist")
   }
 }
}
