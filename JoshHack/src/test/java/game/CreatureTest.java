package game;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.awt.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CreatureTest {

	Creature myPlayerCreature;
	String causeOfDeath;
	World myWorld;
	Tile[][][] myTiles;
	Item myItem;

	@BeforeEach
	public void setUp() {
		
		//I used AI to add the myTiles part because my isDead tests kept crashing for no reason and I had no clue what was going on
		//This did not impact my exam answers at all
		myTiles = new Tile[3][3][1];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				myTiles[x][y][0] = Tile.FLOOR;  
			}
		}
		//end

		myWorld = new World(myTiles);
		myItem = new Item('A',Color.BLUE,"string1", "string2");
		myPlayerCreature = new Creature(myWorld, '@', Color.RED, "Testing", 100, 15, 10);
	}

	@AfterEach
	public void tearDown() {
		myTiles = null;
		myWorld = null;
		myPlayerCreature = null;
	}

	@Test
	public void testAdd_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(1);
		assertEquals(16, myPlayerCreature.attackValue());
	}

	@Test
	public void testZero_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(0);
		assertEquals(15, myPlayerCreature.attackValue());
	}

	@Test
	public void testSubtract_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(-1);
		assertEquals(14, myPlayerCreature.attackValue());
	}

	@Test
	public void testConfirmsPlayerGlyph() {
		//Creature was established with a glyph of @
		//ASSERT
		assertTrue(myPlayerCreature.isPlayer());
	}

	@Test
	public void testConfirmsNonPlayerGlyph() {
		//Creature was established with a glyph of C
		Creature myNonPlayerCreature = new Creature(myWorld, 'C', Color.RED, "Testing", 100, 15, 10);
		//ASSERT
		assertFalse(myNonPlayerCreature.isPlayer());
	}

	//EXAM 1, incomplete
    @ParameterizedTest
    @CsvSource ({
        "10, 100",
		"-10, 90",
		"-100, 0",
    })
    public void modifyHpCalcNewHpTest(int amount, int expectedHp) {
		//ACT
		//Note: Original HP is 100
		myPlayerCreature.modifyHp(amount, "Stabbed");
		//ASSERT
		assertEquals(expectedHp, myPlayerCreature.hp());

    }


	@Test
    public void modifyHpCauseOfDeathTest() {
		myPlayerCreature.modifyHp(50, "Stabbed");

		assertEquals("Stabbed", myPlayerCreature.causeOfDeath());
    }

}

