package dummy.MahjonggCalc.test;

import dummy.MahjonggCalc.RoundScoreCalculator;
import android.test.AndroidTestCase;

public class RoundScoreCalculatorTest extends AndroidTestCase {
    public void testEmptyResult() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	Integer result[][] = calculator.getResult();
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; x < 4; x++) {
    			assertNull(result[x][y]);
    		}
      }

    private void assertEqualArray(Integer[][] array1, Integer[][] array2) {
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; x < 4; x++) {
    			assertEquals(array1[x][y], array2[x][y]);
    		}
    }
    
    public void testEmptyResult2() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	Integer result[][] = calculator.getResult();
		Integer retval [][] = new Integer[][] {
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null}
				};
		assertEqualArray(retval, result);
		
    }

    public void testTwoPlayers() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	calculator.setPlayerScore(0, 10);
    	calculator.setPlayerScore(1, 20);
    	
    	Integer result[][] = calculator.getResult();
    	Integer retval [][] = new Integer[][] {
    			new Integer[] {null, -10, null, null},
    			new Integer[] {10, null, null, null},
    			new Integer[] {null, null, null, null},
    			new Integer[] {null, null, null, null}
    	};
    	assertEqualArray(retval, result);
    	
    }
}
