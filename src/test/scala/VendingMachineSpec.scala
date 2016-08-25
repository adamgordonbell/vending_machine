import data._
import org.scalatest._

import scala.collection.mutable.{Map => MMap, ListBuffer => MList}

class VendingMachineSpec extends FlatSpec with Matchers with EitherValues {

  //Change machine is stocked with a list of the coins it has to offer for change
  val change =
  List.fill(25)(25) ++
    List.fill(10)(10) ++
    List.fill(5)(10) ++
    List.fill(1)(10)

  //Vending machine takes its inventory and change on start up
  val machine = new VendingMachine(Inventory(MMap(
    ('a', 1) -> InventoryItem(Product("Snickers"), 105, 10),
    ('a', 2) -> InventoryItem(Product("Mars"), 95, 1)
  )), change)

  //Change a user has for paying for items
  val pocketChange = List(25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 10, 5)

  it should "not allow buying of product that isn't there" in {
    val response = machine.buyProductWithBalance('a', 15)
    response.left.value should be("Product does not exist")
  }

  it should "require payment for a product" in {
    val response = machine.buyProductWithBalance('a', 1)
    response.left.value should be("Insufficent Funds")
  }

  it should "not sell more than it has" in {
    machine.addToBalance(pocketChange)
    val first = machine.buyProductWithBalance('a', 2)
    val second = machine.buyProductWithBalance('a', 2)
    second.left.value should be("Product Out Of Stock")
    machine.withdrawBalance()
  }

  it should "give change back" in {
    machine.addToBalance(pocketChange)
    val item = machine.buyProductWithBalance('a', 1)
    val change = machine.withdrawBalance()
    change should be(List(25, 25, 25, 25, 25, 25, 25, 10))
  }

  it should "support configurable indexes" in {
    val alphaFruitMachine = new VendingMachine(Inventory(MMap(
      'A' -> InventoryItem(Product("Apple"), 1, 20),
      'B' -> InventoryItem(Product("Orange"), 2, 25)
    )), change)

    val numericFruitMachine = new VendingMachine(Inventory(MMap(
      1 -> InventoryItem(Product("Apple"), 1, 20),
      2 -> InventoryItem(Product("Orange"), 2, 25)
    )), change)

    alphaFruitMachine.addToBalance(pocketChange)
    numericFruitMachine.addToBalance(pocketChange)

    val itemA = alphaFruitMachine.buyProductWithBalance('A')
    val item1 = numericFruitMachine.buyProductWithBalance(1)

    itemA.right.value should be(Product("Apple"))
    item1.right.value should be(Product("Apple"))
  }
}
