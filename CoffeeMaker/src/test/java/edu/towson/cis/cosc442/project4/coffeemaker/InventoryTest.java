package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class InventoryTest {
    Inventory inventory;
    Recipe recipe;

    @BeforeEach 
    void setUp() {
        //ARRANGE
        inventory = new Inventory();
        recipe = new Recipe();
        recipe.setAmtChocolate(1);
        recipe.setAmtCoffee(2);
        recipe.setAmtMilk(1);
        recipe.setAmtSugar(1);
    }

    @AfterEach 
    void tearDown() {
        inventory = null;
        recipe = null;
    }


    // note: Inventory is default set to 15 for every value
    @Test 
    public void inventoryTest() {
        //ASSERT
        assertEquals(15, inventory.getChocolate());
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());

    }


    //------Set Amount Chocolate in Inventory
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "1, 1",    // positive
        "0, 0",    // zero
        "-1, 0",    // negative: stays at 0
        "-5, 0",    // negative: stays at 0
    })
    public void getAndSetInventoryChocolateTest(int input, int expected) {
        //ACT
        inventory.setChocolate(input);

        //ASSERT
        assertEquals(expected, inventory.getChocolate());
    }

    //------Set Amount Coffee in Inventory
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetInventoryCoffeeTest(int input, int expected) {
        //ACT
        inventory.setCoffee(input);

        //ASSERT
        assertEquals(expected, inventory.getCoffee());
    }

    //------Set Amount Milk in Inventory
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetInventoryMilkTest(int input, int expected) {
        //ACT
        inventory.setMilk(input);

        //ASSERT
        assertEquals(expected, inventory.getMilk());
    }


    //------Set Amount Sugar in Inventory
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetInventorySugarTest(int input, int expected) {
        //ACT
        inventory.setSugar(input);

        //ASSERT
        assertEquals(expected, inventory.getSugar());
    }


    @Test 
    public void isEnoughIngredients() {
        //the ingredients are all default set to 15
        assertTrue(inventory.enoughIngredients(recipe));

    }

    @Test 
    public void isJustEnoughIngredients() {
        inventory.setChocolate(1);
        inventory.setCoffee(2);
        inventory.setMilk(1);
        inventory.setSugar(1);
       
        assertTrue(inventory.enoughIngredients(recipe));

    }

    @Test 
    public void isNotEnoughChocolateIngredients() {
        //the rest of the ingredients are default set to 15
        inventory.setChocolate(0);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test 
    public void isNotEnoughCoffeeIngredients() {
        //the rest of the ingredients are default set to 15
        inventory.setCoffee(1);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test 
    public void isNotEnoughMilkIngredients() {
       //the rest of the ingredients are default set to 15
        inventory.setMilk(0);

        assertFalse(inventory.enoughIngredients(recipe)); 
    }

    @Test 
    public void isNotEnoughSugarIngredients() {
        //the rest of the ingredients are default set to 15
        inventory.setSugar(0);

        assertFalse(inventory.enoughIngredients(recipe));
    }

}
