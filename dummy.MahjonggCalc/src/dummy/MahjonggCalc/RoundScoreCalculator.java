package dummy.MahjonggCalc;

public class RoundScoreCalculator {
	private Integer playerScores[] = new Integer[] {null, null, null, null};
	
	public void setPlayerScore(int player, int score) {
		playerScores[player] = score;
	}
	
	public Integer[][] getResult() {
		Integer retval [][] = new Integer[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null}
				};
		
    	for (int x = 0; x < 4; x++)
    		for (int y = x + 1; y < 4; y++) 
    			if (playerScores[x] != null && playerScores[y] != null)
	    		{
	    			retval[x][y] = playerScores[x] - playerScores[y];
	    			retval[y][x] = playerScores[y] - playerScores[x];
	    		}
		
		return retval;
	}
}
