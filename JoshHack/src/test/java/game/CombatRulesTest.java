package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CombatRulesTest {

    CombatRules combatRules;

    @BeforeEach
	public void setUp() {
        combatRules = new CombatRules();
	}

	@AfterEach
	public void tearDown() {
		combatRules = null;
	}


    @ParameterizedTest 
    @CsvSource({    
        "50, -50, 0",    
        "50, -49, 1",
        "50, 49, 99",
        "50, 50, 100",
        "50, 51, 100",
    })
    public void calculateNewHpTest(int currentHp, int amount, int expected) {
        assertEquals(expected, combatRules.calculateNewHp(currentHp, 100, amount));
	}
	
    
    @Test 
	public void isLessThanOneDeadTest() {
        assertTrue(combatRules.isDead(0));
	}

    @Test 
	public void isGreaterThanOneDeadTest() {
        assertFalse(combatRules.isDead(10));
	}

    @Test 
    public void isEqualToOneDeadTest() {
        assertFalse(combatRules.isDead(1));
	}

}