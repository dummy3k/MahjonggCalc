package dummy.MahjonggCalc;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RoundScoreCalculatorTest extends TestCase {
    public void testEmptyResult() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	Integer result[][] = calculator.getResult();
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; y < 4; y++) {
    			Assert.assertNull(result[x][y]);
    		}
      }

    private void assertEqualArray(Integer[][] array1, Integer[][] array2) {
    	for (int x = 0; x < 4; x++)
    		for (int y = 0; y < 4; y++) {
    			Assert.assertEquals(array1[x][y], array2[x][y]);
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
    	calculator.setEastPlayer(2);
    	
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
    	calculator.setEastPlayer(2);
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, -10, null, -40},
    			{10, null, null, -30},
    			{null, null, null, null},
    			{40, 30, null, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }

    public void testThreePlayersWithEast() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	calculator.setPlayerScore(0, 10);
    	calculator.setPlayerScore(1, 20);
    	calculator.setPlayerScore(3, 50);
    	calculator.setEastPlayer(1);
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, -20, null, -40},
    			{20, null, null, -60},
    			{null, null, null, null},
    			{40, 60, null, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }

    public void testThreePlayersWithWinner() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	calculator.setPlayerScore(0, 10);
    	calculator.setPlayerScore(1, 20);
    	calculator.setPlayerScore(3, 50);
    	calculator.setEastPlayer(2);
    	calculator.setWinner(1);
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, -20, null, -40},
    			{20, null, 40, 20},
    			{null, -40, null, null},
    			{40, -20, null, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }

    public void testFourPlayersWithEastWinner() {
    	RoundScoreCalculator calculator = new RoundScoreCalculator();
    	calculator.setPlayerScore(0, 70);
    	calculator.setPlayerScore(1, 20);
    	calculator.setPlayerScore(2, 50);
    	calculator.setPlayerScore(3, 30);
    	calculator.setEastPlayer(3);
    	calculator.setWinner(3);
    	
    	Integer actualResult[][] = calculator.getResult();
    	Integer expectedResult [][] = new Integer[][] {
    			{null, 50, 20, -60},
    			{-50, null, -30, -60},
    			{-20, 30, null, -60},
    			{60, 60, 60, null}
    	};
    	assertEqualArray(expectedResult, actualResult);
    }
}
