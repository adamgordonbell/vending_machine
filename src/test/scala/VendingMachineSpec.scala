import data._
import org.scalatest._

import scala.collection.mutable.{Map => MMap}

class VendingMachineSpec extends FlatSpec with Matchers with EitherValues {
  val machine = new VendingMachine(Inventory(MMap(
      ('a',1) ->  InventoryItem(Product("Snickers"),1.05,10),
      ('a',2) -> InventoryItem(Product("Mars"),0.95,1)
    )))

  it should "not allow buying of product that isn't there" in {
    val response: Either[String, Product] = machine.buyProductWithBalance('a',15)
    response.left.value should be ("Product does not exist")
  }

  it should "require payment for a product" in {
    val response: Either[String, Product] = machine.buyProductWithBalance('a',1)
    response.left.value should be ("Insufficent Funds")
  }

  it should "not sell more than it has" in {
    machine.addToBalance(10)
    val first = machine.buyProductWithBalance('a',2)
    val second = machine.buyProductWithBalance('a',2)
    machine.withdrawBalance()
    second.left.value should be ("Product Out Of Stock")
  }

  it should "give change back" in {
    machine.addToBalance(10)
    val item = machine.buyProductWithBalance('a',1)
    val change = machine.withdrawBalance()
    change should be (8.95)
  }
}


// questions
// Do I actually build and API, or just the code the api should call
//    -- I would just expose this a some play endpoints in real world
// Does concurrency matter
//  -- I have never seen a concurrent vending machine
// Does making change matter?
// -- making change is about the only challenging part I can think of in this domain