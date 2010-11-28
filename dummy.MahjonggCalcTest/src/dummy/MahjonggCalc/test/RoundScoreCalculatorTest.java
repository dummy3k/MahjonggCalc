package dummy.MahjonggCalc.test;

import dummy.MahjonggCalc.RoundScoreCalculator;
import android.test.AndroidTestCase;

public class RoundScoreCalculatorTest extends AndroidTestCase {
    public void testEmptyResult() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	Integer result[][] = calculator.getResult();
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; y < 4; y++) {
    			assertNull(result[x][y]);
    		}
      }

    private void assertEqualArray(Integer[][] array1, Integer[][] array2) {
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; y < 4; y++) {
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
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, -10, null, null},
    			{10, null, null, null},
    			{null, null, null, null},
    			{null, null, null, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }

    public void testThreePlayers() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	calculator.setPlayerScore(0, 10);
    	calculator.setPlayerScore(1, 20);
    	calculator.setPlayerScore(3, 50);
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, -10, null, -40},
    			{10, null, null, -30},
    			{null, null, null, null},
    			{40, 30, null, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }
}
