package edu.towson.cis.cosc442.project4.coffeemaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class CoffeeMakerTest {
    CoffeeMaker coffeeMaker;
    Recipe chai;
    Recipe dirtyChai;
    Inventory inventory;
    

    @BeforeEach 
    void setUp() {
        //ARRANGE
        coffeeMaker = new CoffeeMaker();
        inventory = new Inventory();

        //ARRANGE
        chai = new Recipe();
        chai.setName("Chai Latte");
        chai.setPrice(4);
        chai.setAmtChocolate(1);
        chai.setAmtCoffee(2);
        chai.setAmtMilk(2);
        chai.setAmtSugar(1);

        dirtyChai = new Recipe();
        dirtyChai.setName("Dirty Chai Latte");
        dirtyChai.setPrice(5);
        dirtyChai.setAmtChocolate(1);
        dirtyChai.setAmtCoffee(3);
        dirtyChai.setAmtMilk(1);
        dirtyChai.setAmtSugar(1);
    }

    @AfterEach 
    void tearDown() {
        chai = null;
        dirtyChai = null;
        coffeeMaker = null;
    }


    // add
    @Test
    public void addValidRecipeTest() {
        //ACT, ASSERT
        assertTrue(coffeeMaker.addRecipe(chai));
    }

    @Test
    public void addAlreadyExistingRecipeTest() {
        //ACT
       coffeeMaker.addRecipe(chai);
       //ASSERT
       assertFalse(coffeeMaker.addRecipe(chai));
    }

    @Test
    public void addNoNameRecipeTest() {
        //ARRANGE
        chai.setName(null);
        //ACT, ASSERT
        assertTrue(coffeeMaker.addRecipe(chai));
    }
   
    @Test
    public void addNullRecipeTest() {
        //ARRANGE
        chai = null;
        //ACT, ASSERT
        assertThrows(Exception.class, () -> {
            coffeeMaker.addRecipe(chai);
        });
    }


    // delete
    @Test
    public void deleteValidRecipeTest() {
        //ACT
       coffeeMaker.addRecipe(chai);
       //ASSERT
       assertTrue(coffeeMaker.deleteRecipe(chai));
    }

    @Test
    public void deleteNoNameRecipeTest() {
       //ASSERT
        assertFalse(coffeeMaker.deleteRecipe(chai));
    }


    // edit
    @Test
    public void editValidRecipeTest() {
        //ACT
       coffeeMaker.addRecipe(chai);
       //ASSERT
       assertTrue(coffeeMaker.editRecipe(chai, dirtyChai));
    }

    @Test
    public void editNoNameRecipeTest() {
        //ACT
       coffeeMaker.addRecipe(chai);
       //ASSERT
       assertFalse(coffeeMaker.editRecipe(dirtyChai, chai));
    }


   
    // add inventory
    @Test
    public void addInventoryTest() {
        //ASSERT
        assertTrue(coffeeMaker.addInventory(10,10,10,10));
    }

    // add inventory
    @ParameterizedTest 
    @CsvSource({
        "0, 1, 1, 1",    
        "1, 0, 1, 1",    
        "1, 1, 0, 1",
        "1, 1, 1, 0",   
    })
    public void addZeroInventoryTest(int amtCoffee, int amtMilk, int amtSugar, int amtChocolate) {
        //ASSERT
        assertTrue(coffeeMaker.addInventory(amtCoffee, amtMilk, amtSugar, amtChocolate));
    }

    @ParameterizedTest 
    @CsvSource({
        "-1, 1, 1, 1",    
        "1, -1, 1, 1",    
        "1, 1, -1, 1",
        "1, 1, 1, -1",   
    })
    public void addNegativeInventoryTest(int amtCoffee, int amtMilk, int amtSugar, int amtChocolate) {
        //ASSERT
        assertFalse(coffeeMaker.addInventory(amtCoffee, amtMilk, amtSugar, amtChocolate));
    }


   
    @Test
    public void checkInventoryTest() {
        //ACT
        //All inventory items are default set to 15
        //ASSERT
        assertEquals(15, coffeeMaker.checkInventory().getCoffee());
        assertEquals(15, coffeeMaker.checkInventory().getMilk());
        assertEquals(15, coffeeMaker.checkInventory().getSugar());
        assertEquals(15, coffeeMaker.checkInventory().getChocolate());
    }


    @Test
    public void makeCoffeeWithInvalidPaymentTest() {
        //ASSERT
        assertEquals(1, coffeeMaker.makeCoffee(chai, 1));
    }

    @Test
    public void makeCoffeeWithValidPaymentTest() {
        //ASSERT
        assertEquals(1, coffeeMaker.makeCoffee(chai, 5));
    }

    @Test
    public void makeCoffeeWithJustEnoughMoneyTest() {
        //ASSERT
        assertEquals(0, coffeeMaker.makeCoffee(chai, 4));
    }

    @Test
    public void makeCoffeeWithInvalidInventoryTest() {
        //ACT
        chai.setAmtCoffee(20);
        //ASSERT
        assertEquals(5, coffeeMaker.makeCoffee(chai, 5));
    }



//---Can make coffee
    @Test 
    public void canMakeCoffeeWithEnoughChocolateTest() {
        //ARRANGE
        inventory.setChocolate(5);
        //ACT
        coffeeMaker.makeCoffee(chai, chai.getPrice());
        //ASSERT
        assertEquals(4, inventory.getChocolate());
    }


    @Test 
    public void canMakeCoffeeWithEnoughCoffeeTest() {
        //ARRANGE
        inventory.setCoffee(5);
        //ACT
        coffeeMaker.makeCoffee(chai, chai.getPrice());
        //ASSERT
        assertEquals(3, inventory.getCoffee());
    }


    @Test 
    public void canMakeCoffeeWithEnoughMilkTest() {
        //ARRANGE
        inventory.setMilk(5);
        //ACT
        coffeeMaker.makeCoffee(chai, chai.getPrice());
        //ASSERT
        assertEquals(3, inventory.getMilk());
    }


    @Test 
    public void canMakeCoffeeWithEnoughSugarTest() {
        //ARRANGE
        inventory.setSugar(5);
        //ACT
        coffeeMaker.makeCoffee(chai, chai.getPrice());
        //ASSERT
        assertEquals(4, inventory.getSugar());
    }

    // I skipped the checkInventory method cause there isn't really any code to test
    // There was only a single return function
    // But it was used in other test methods


    @Test
    public void getRecipeForValidNameTest() {
        //ACT
       coffeeMaker.addRecipe(chai);

       //ASSERT
       assertEquals(chai, coffeeMaker.getRecipeForName("Chai Latte"));
    }

    @Test
    public void getRecipeForInvalidNameTest() {
        
        //ASSERT
        assertNull(coffeeMaker.getRecipeForName("Non-Existing Name").getName());
    }
}