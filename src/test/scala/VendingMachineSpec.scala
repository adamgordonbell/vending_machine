import data._
import org.scalatest._

import scala.collection.mutable.{Map => MMap, ListBuffer => MList}

class VendingMachineSpec extends FlatSpec with Matchers with EitherValues {
  val change = Change(MList(25,25,25,25,25,25,25,25,25,10,10,10,10,10,10,10,10,10,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1))
  val machine = new VendingMachine(Inventory(MMap(
      ('a',1) ->  InventoryItem(Product("Snickers"),105,10),
      ('a',2) -> InventoryItem(Product("Mars"),95,1)
    )), change)
  val pocketChange = List(25,25,25,25,25,25,25,25,25,25,25,10,5)

  it should "not allow buying of product that isn't there" in {
    val response: Either[String, Product] = machine.buyProductWithBalance('a',15)
    response.left.value should be ("Product does not exist")
  }

  it should "require payment for a product" in {
    val response: Either[String, Product] = machine.buyProductWithBalance('a',1)
    response.left.value should be ("Insufficent Funds")
  }

  it should "not sell more than it has" in {
    machine.addToBalance(pocketChange)
    val first = machine.buyProductWithBalance('a',2)
    val second = machine.buyProductWithBalance('a',2)
    machine.withdrawBalance()
    second.left.value should be ("Product Out Of Stock")
  }

  it should "give change back" in {
    machine.addToBalance(pocketChange)
    val item = machine.buyProductWithBalance('a',1)
    val change = machine.withdrawBalance()
    change should be (8.95)
  }

  it should "support configurable indexes" in {
    val machine = new VendingMachine(Inventory(MMap(
      'A' ->  InventoryItem(Product("Apple"),1,20),
      'B' -> InventoryItem(Product("Orange"),2,25)
    )), change)
    machine.addToBalance(pocketChange)
    val item = machine.buyProductWithBalance('A')
    item.right.value should be(Product("Apple"))
  }
}
