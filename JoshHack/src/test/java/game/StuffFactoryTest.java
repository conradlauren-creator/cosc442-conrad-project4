package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StuffFactoryTest {

    private World world;
    private StuffFactory factory;
    private Tile[][][] tiles;

    @BeforeEach
    public void setUp() {
        tiles = new Tile[5][5][1];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y][0] = Tile.FLOOR;
            }
        }
        world = new World(tiles);
        factory = new StuffFactory(world);
    }

    @Test
    public void daggerFactorySetsDaggerStats() {
        Item item = factory.newDagger(0);

        assertEquals("dagger", item.name());
        assertEquals(5, item.attackValue());
        assertEquals(6, item.thrownAttackValue());
    }

    @Test
    public void swordFactorySetsSwordStats() {
        Item item = factory.newSword(0);

        assertEquals("sword", item.name());
        assertEquals(10, item.attackValue());
        assertEquals(4, item.thrownAttackValue());
    }

    @Test
    public void staffFactorySetsStaffStats() {
        Item item = factory.newStaff(0);

        assertEquals("staff", item.name());
        assertEquals(5, item.attackValue());
        assertEquals(3, item.defenseValue());
        assertEquals(4, item.thrownAttackValue());
    }

    @Test
    public void bowFactorySetsBowStats() {
        Item item = factory.newBow(0);

        assertEquals("bow", item.name());
        assertEquals(1, item.attackValue());
        assertEquals(5, item.rangedAttackValue());
    }

    @Test
    public void edibleWeaponFactorySetsAttackAndFoodStats() {
        Item item = factory.newEdibleWeapon(0);

        assertEquals("baguette", item.name());
        assertEquals(3, item.attackValue());
        assertEquals(100, item.foodValue());
    }

    @Test
    public void lightArmorFactorySetsDefenseStats() {
        Item item = factory.newLightArmor(0);

        assertEquals("tunic", item.name());
        assertEquals(2, item.defenseValue());
    }

    @Test
    public void mediumArmorFactorySetsDefenseStats() {
        Item item = factory.newMediumArmor(0);

        assertEquals("chainmail", item.name());
        assertEquals(4, item.defenseValue());
    }

    @Test
    public void heavyArmorFactorySetsDefenseStats() {
        Item item = factory.newHeavyArmor(0);

        assertEquals("platemail", item.name());
        assertEquals(6, item.defenseValue());
    }

    @Test
    public void randomWeaponFactoryReachesAllWeaponPartitions() {
        Set<String> names = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            clearItems();
            names.add(factory.randomWeapon(0).name());
        }

        assertEquals(Set.of("dagger", "sword", "bow"), names);
    }

    @Test
    public void randomArmorFactoryReachesAllArmorPartitions() {
        Set<String> names = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            clearItems();
            names.add(factory.randomArmor(0).name());
        }

        assertEquals(Set.of("tunic", "chainmail", "platemail"), names);
    }

    @Test
    public void healthPotionFactoryCreatesEffectWithFullHpBoundary() {
        Item potion = factory.newPotionOfHealth(0);
        Creature creature = newCreature("health creature");
        creature.modifyHp(-10, "test damage");

        assertEquals("health potion", potion.name());
        assertNotNull(potion.quaffEffect());

        potion.quaffEffect().start(creature);
        assertEquals(creature.maxHp(), creature.hp());

        Creature fullHpCreature = newCreature("full health creature");
        potion.quaffEffect().start(fullHpCreature);
        assertEquals(fullHpCreature.maxHp(), fullHpCreature.hp());
    }

    @Test
    public void manaPotionFactoryCreatesEffectWithFullManaBoundary() {
        Item potion = factory.newPotionOfMana(0);
        Creature creature = newCreature("mana creature");
        creature.modifyMana(-5);

        assertEquals("mana potion", potion.name());
        assertNotNull(potion.quaffEffect());

        potion.quaffEffect().start(creature);
        assertEquals(creature.maxMana(), creature.mana());

        Creature fullManaCreature = newCreature("full mana creature");
        potion.quaffEffect().start(fullManaCreature);
        assertEquals(fullManaCreature.maxMana(), fullManaCreature.mana());
    }

    @Test
    public void slowHealthPotionFactoryCreatesEffect() {
        Item potion = factory.newPotionOfSlowHealth(0);
        Creature creature = newCreature("slow health creature");
        creature.modifyHp(-10, "test damage");

        assertEquals("slow health potion", potion.name());
        assertNotNull(potion.quaffEffect());

        potion.quaffEffect().start(creature);
        potion.quaffEffect().update(creature);
        assertEquals(91, creature.hp());
    }

    @Test
    public void poisonPotionFactoryCreatesEffect() {
        Item potion = factory.newPotionOfPoison(0);
        Creature creature = newCreature("poison creature");

        assertEquals("poison potion", potion.name());
        assertNotNull(potion.quaffEffect());

        potion.quaffEffect().start(creature);
        potion.quaffEffect().update(creature);
        assertEquals(99, creature.hp());
    }

    @Test
    public void warriorPotionFactoryAddsAndRemovesStats() {
        Item potion = factory.newPotionOfWarrior(0);
        Creature creature = newCreature("warrior");

        assertEquals("warrior's potion", potion.name());
        potion.quaffEffect().start(creature);
        assertEquals(20, creature.attackValue());
        assertEquals(15, creature.defenseValue());

        potion.quaffEffect().end(creature);
        assertEquals(15, creature.attackValue());
        assertEquals(10, creature.defenseValue());
    }

    @Test
    public void archerPotionFactoryAddsAndRemovesVision() {
        Item potion = factory.newPotionOfArcher(0);
        Creature creature = newCreature("archer");

        assertEquals("archers potion", potion.name());
        potion.quaffEffect().start(creature);
        assertEquals(12, creature.visionRadius());

        potion.quaffEffect().end(creature);
        assertEquals(9, creature.visionRadius());
    }

    @Test
    public void experiencePotionFactoryModifiesExperience() {
        Item potion = factory.newPotionOfExperience(0);
        Creature creature = newCreature("experienced creature");

        assertEquals("experience potion", potion.name());
        potion.quaffEffect().start(creature);
        assertEquals(5, creature.xp());
    }

    @Test
    public void randomPotionFactoryReachesAllPotionPartitions() {
        Set<String> names = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            clearItems();
            names.add(factory.randomPotion(0).name());
        }

        assertEquals(Set.of(
                "health potion",
                "mana potion",
                "slow health potion",
                "poison potion",
                "warrior's potion",
                "archers potion",
                "experience potion"),
                names);
    }

    @Test
    public void weaponArmorAndPotionFactoriesPlaceItemsInTheWorld() {
        Item[] items = {
                factory.newDagger(0),
                factory.newSword(0),
                factory.newStaff(0),
                factory.newBow(0),
                factory.newLightArmor(0),
                factory.newMediumArmor(0),
                factory.newHeavyArmor(0),
                factory.newPotionOfHealth(0),
                factory.newPotionOfMana(0),
                factory.newPotionOfSlowHealth(0),
                factory.newPotionOfPoison(0),
                factory.newPotionOfWarrior(0),
                factory.newPotionOfArcher(0),
                factory.newPotionOfExperience(0)
        };

        for (Item item : items) {
            assertTrue(isPlacedInWorld(item));
        }
    }

    private boolean isPlacedInWorld(Item item) {
        for (int x = 0; x < world.width(); x++) {
            for (int y = 0; y < world.height(); y++) {
                if (world.item(x, y, 0) == item) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clearItems() {
        for (int x = 0; x < world.width(); x++) {
            for (int y = 0; y < world.height(); y++) {
                world.remove(x, y, 0);
            }
        }
    }

    private Creature newCreature(String name) {
        Creature creature = new Creature(world, 'C', Color.RED, name, 100, 15, 10);
        creature.setCreatureAi(new CreatureAi(creature));
        creature.x = 2;
        creature.y = 2;
        creature.z = 0;
        world.add(creature);
        return creature;
    }
}
