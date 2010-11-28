package dummy.MahjonggCalc;

public class RoundScoreCalculator {
	private Integer playerScores[] = new Integer[] {null, null, null, null};
	
	public void setPlayerScore(int player, int score) {
		playerScores[player] = score;
	}
	
	public Integer[][] getResult() {
		Integer retval [][] = new Integer[][] {
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null},
				new Integer[] {null, null, null, null}
				};
		
    	for (int x = 0; x < 4; x++)
    		for (int y = x + 1; x < 4; x++) 
    			if (playerScores[x] != null && playerScores[x] != null)
	    		{
	    			retval[x][y] = playerScores[x] - playerScores[y];
	    			retval[y][x] = playerScores[y] - playerScores[x];
	    		}
		
		return retval;
	}
}
