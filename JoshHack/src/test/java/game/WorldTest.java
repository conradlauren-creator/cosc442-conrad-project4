package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorldTest {

    private Tile[][][] tiles;
    private World world;

    @BeforeEach
    public void setUp() {
        tiles = new Tile[3][3][2];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                for (int z = 0; z < tiles[x][y].length; z++) {
                    tiles[x][y][z] = Tile.FLOOR;
                }
            }
        }
        world = new World(tiles);
    }

    @Test
    public void constructorReportsWorldDimensions() {
        assertEquals(3, world.width());
        assertEquals(3, world.height());
        assertEquals(2, world.depth());
    }

    @Test
    public void outOfBoundsCoordinatesReturnBoundsTile() {
        assertEquals(Tile.BOUNDS, world.tile(-1, 0, 0));
        assertEquals(Tile.BOUNDS, world.tile(3, 0, 0));
        assertEquals(Tile.BOUNDS, world.tile(0, 3, 0));
        assertEquals(Tile.BOUNDS, world.tile(0, 0, 2));
    }

    @Test
    public void digOnlyChangesDiggableWall() {
        tiles[1][1][0] = Tile.WALL;

        world.dig(1, 1, 0);
        assertEquals(Tile.FLOOR, world.tile(1, 1, 0));

        world.dig(0, 0, 0);
        assertEquals(Tile.FLOOR, world.tile(0, 0, 0));
    }

    @Test
    public void creatureCanBeAddedFoundAndRemoved() {
        Creature creature = new Creature(world, '@', Color.RED, "player", 100, 15, 10);
        creature.x = 1;
        creature.y = 1;
        creature.z = 0;

        world.add(creature);

        assertSame(creature, world.creature(1, 1, 0));
        world.remove(creature);
        assertEquals(null, world.creature(1, 1, 0));
    }

    @Test
    public void itemCanBePlacedFoundAndRemovedByObject() {
        Item item = new Item('r', Color.RED, "rock", null);

        assertTrue(world.addAtEmptySpace(item, 1, 1, 0));
        assertSame(item, world.item(1, 1, 0));

        world.remove(item);
        assertEquals(null, world.item(1, 1, 0));
    }

    @Test
    public void itemCanBeRemovedByCoordinates() {
        Item item = new Item('r', Color.RED, "rock", null);
        assertTrue(world.addAtEmptySpace(item, 1, 1, 0));

        world.remove(1, 1, 0);

        assertEquals(null, world.item(1, 1, 0));
    }

    @Test
    public void itemCanBePlacedInNearbyEmptySpace() {
        Item occupied = new Item('a', Color.BLUE, "occupied", null);
        Item dropped = new Item('b', Color.GREEN, "dropped", null);
        assertTrue(world.addAtEmptySpace(occupied, 1, 1, 0));

        assertTrue(world.addAtEmptySpace(dropped, 1, 1, 0));
        boolean droppedWasPlacedNearby = false;
        for (int x = 0; x < world.width(); x++) {
            for (int y = 0; y < world.height(); y++) {
                if (world.item(x, y, 0) == dropped) {
                    droppedWasPlacedNearby = true;
                }
            }
        }
        assertTrue(droppedWasPlacedNearby);
    }
}
