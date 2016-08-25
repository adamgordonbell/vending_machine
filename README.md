Problem:
Create a vending machine in the language of your choice. 
The vending machine can take any currency you wish, and can sell any products you wish. 
The vending machine can either take the form of an API or a UI (you do not have to implement both). 
The vending machine should respond to basic commands, such as pay and select_products.


Assumptions / Thoughts
 * I am assuming this is a single user API, as vending machines are one person at a time
  * because of this I don't worry about concurrency
 * I have chosen to make this api callable from with a jvm process
  * therefore I am not listening on port for requests for instance
 * I have chosen to have coins represented as Ints
  *  25,10,5,1 represent quarters, dimes, nickels and pennies
 * I have decided to write some test cases to document how this API is called 
 
 
 