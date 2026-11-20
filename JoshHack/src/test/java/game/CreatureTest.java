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
		
		//ARRANGE
		myTiles = new Tile[3][3][1];

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				myTiles[x][y][0] = Tile.FLOOR;
			}
		}

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
	public void testCreatureFoodInitialization() {
		//assert
		assertEquals(1000/3*2, myPlayerCreature.food());
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

	@Test
	public void creatureDimensionAndStateGettersReportValues() {
		assertEquals('@', myPlayerCreature.glyph());
		assertEquals(Color.RED, myPlayerCreature.color());
		assertEquals(100, myPlayerCreature.maxHp());
		assertEquals(100, myPlayerCreature.hp());
		assertEquals(15, myPlayerCreature.attackValue());
		assertEquals(10, myPlayerCreature.defenseValue());
		assertEquals(9, myPlayerCreature.visionRadius());
		assertEquals("Testing", myPlayerCreature.name());
	}

	@Test
	public void manaIsClampedToMaximum() {
		myPlayerCreature.modifyMana(100);
		assertEquals(5, myPlayerCreature.mana());

		myPlayerCreature.modifyMana(-100);
		assertEquals(0, myPlayerCreature.mana());
	}

	@Test
	public void modifyMaxManaAndManaSupportMaximumChanges() {
		myPlayerCreature.modifyMaxMana(5);
		assertEquals(10, myPlayerCreature.maxMana());

		myPlayerCreature.modifyMana(5);
		assertEquals(10, myPlayerCreature.mana());
	}

	@Test
	public void weaponAndArmorContributeToEffectiveValues() {
		Item weapon = new Item(')', Color.WHITE, "sword", null);
		weapon.modifyAttackValue(10);
		Item armor = new Item('[', Color.BLUE, "armor", null);
		armor.modifyDefenseValue(6);
		myPlayerCreature.setCreatureAi(new CreatureAi(myPlayerCreature));

		myPlayerCreature.inventory().add(weapon);
		myPlayerCreature.inventory().add(armor);
		myPlayerCreature.equip(weapon);
		myPlayerCreature.equip(armor);

		assertEquals(25, myPlayerCreature.attackValue());
		assertEquals(16, myPlayerCreature.defenseValue());
	}

	@Test
	public void movementToEmptyTileUpdatesCreatureCoordinates() {
		myPlayerCreature.setCreatureAi(new CreatureAi(myPlayerCreature));
		myPlayerCreature.x = 1;
		myPlayerCreature.y = 1;
		myPlayerCreature.z = 0;
		myWorld.add(myPlayerCreature);

		myPlayerCreature.moveBy(1, 0, 0);

		assertEquals(2, myPlayerCreature.x);
		assertEquals(1, myPlayerCreature.y);
		assertEquals(665, myPlayerCreature.food());
	}

	@Test
	public void movementIntoWallDoesNotChangeCoordinates() {
		myTiles[1][1][0] = Tile.WALL;
		myWorld = new World(myTiles);
		myPlayerCreature = new Creature(myWorld, '@', Color.RED, "Testing", 100, 15, 10);
		myPlayerCreature.setCreatureAi(new CreatureAi(myPlayerCreature));
		myPlayerCreature.x = 0;
		myPlayerCreature.y = 1;
		myPlayerCreature.z = 0;
		myWorld.add(myPlayerCreature);

		myPlayerCreature.moveBy(1, 0, 0);

		assertEquals(0, myPlayerCreature.x);
		assertEquals(1, myPlayerCreature.y);
	}

	@Test
	public void attackWhenDefenseExceedsAttackStillDocumentsOneDamageRule() {
		Creature defender = new Creature(myWorld, 'C', Color.RED, "defender", 100, 5, 20);
		defender.setCreatureAi(new CreatureAi(defender));
		myPlayerCreature.setCreatureAi(new CreatureAi(myPlayerCreature));

		myPlayerCreature.meleeAttack(defender);

		assertEquals(99, defender.hp());
	}

	@Test
	public void killingCreatureRemovesItFromWorld() {
		Creature defender = new Creature(myWorld, 'C', Color.RED, "defender", 1, 5, 0);
		defender.setCreatureAi(new CreatureAi(defender));
		defender.x = 1;
		defender.y = 1;
		defender.z = 0;
		myWorld.add(defender);
		myPlayerCreature.setCreatureAi(new CreatureAi(myPlayerCreature));

		myPlayerCreature.meleeAttack(defender);

		assertTrue(defender.hp() < 1);
		assertEquals(null, myWorld.creature(1, 1, 0));
	}
}

