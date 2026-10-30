package edu.towson.cis.cosc442.project4.coffeemaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class RecipeTest {
    Recipe recipe;
    Recipe copycatRecipe;
    Recipe notACopycatRecipe;

    @BeforeEach 
    void setUp() {
        //ARRANGE
       recipe = new Recipe();
       recipe.setName("Espresso");

       copycatRecipe = new Recipe();
       copycatRecipe.setName("Espresso");

       notACopycatRecipe = new Recipe();
       notACopycatRecipe.setName("Frappucino");
    }

    @AfterEach 
    void tearDown() {
       recipe = null;
       copycatRecipe = null;
       notACopycatRecipe = null;
    }

    // I skipped the getAmt method cause there isn't really any code to test
    // There was only a single return function. But it is tested in the setAmt methods

    //------Set Amount Chocolate
   
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetRecipeAmtChocolateTest(int input, int expected) {
        //ACT
        recipe.setAmtChocolate(input);

        //ASSERT
        assertEquals(expected, recipe.getAmtChocolate());
    }


    //-----Set amount Coffee
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetRecipeAmtCoffeeTest(int input, int expected) {
        //ACT
        recipe.setAmtCoffee(input);

        //ASSERT
        assertEquals(expected, recipe.getAmtCoffee());
    }


    //-----Set amount Milk
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetRecipeAmtMilk(int input, int expected) {
        //ACT
        recipe.setAmtMilk(input);

        //ASSERT
        assertEquals(expected, recipe.getAmtMilk());
    }


    //-----Set amount Sugar
    @ParameterizedTest 
    @CsvSource({
        "5, 5",    // positive
        "0, 0",    // zero
        "-1, 0"    // negative: stays at 0
    })
    public void getAndSetRecipeAmtSugar(int input, int expected) {
        //ACT
        recipe.setAmtSugar(input);

        //ASSERT
        assertEquals(expected, recipe.getAmtSugar());
    }


    //-----Set and Get Name

    @Test
    public void getAndSetName() {
        //ACT
        recipe.setName("Lasagna");

        //ASSERT
        assertEquals("Lasagna", recipe.getName());
    }



    //-----Set and Get Price
    @Test
    public void getAndSetPrice() {
        //ACT
        recipe.setPrice(4);

        //ASSERT
        assertEquals(4, recipe.getPrice());
    }


   @Test
    public void sameNameEqualsTest() {
        //ASSERT
        assertTrue(recipe.equals(copycatRecipe));
    }

    @Test
    public void differentNameEqualsTest() {
        //ASSERT
        assertFalse(recipe.equals(notACopycatRecipe));
    }
}
