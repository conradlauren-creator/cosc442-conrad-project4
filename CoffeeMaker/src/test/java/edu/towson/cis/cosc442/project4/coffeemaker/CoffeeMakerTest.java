package edu.towson.cis.cosc442.project4.coffeemaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CoffeeMakerTest {
    CoffeeMaker coffeeMaker;
    Recipe chai;
    Recipe dirtyChai;
    

    @BeforeEach 
    void setUp() {
        //ARRANGE
        coffeeMaker = new CoffeeMaker();

        chai = new Recipe();
        chai.setName("Chai Latte");
        chai.setPrice(4);

        dirtyChai = new Recipe();
        dirtyChai.setName("Dirty Chai Latte");
        dirtyChai.setPrice(5);
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
    @Test
    public void addZeroInventoryTest() {
        //ASSERT
        assertTrue(coffeeMaker.addInventory(0,10,10,10));
    }

    @Test
    public void addNegativeInventoryTest() {
        //ASSERT
        assertFalse(coffeeMaker.addInventory(-10,10,10,10));
    }


    // add inventory
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


    // I skipped the checkInventory method cause there isn't really any code to test
    // There was only a single return function


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