package dummy.MahjonggCalc;

public class RoundScoreCalculator {
	private Integer playerScores[] = new Integer[4];
	private Integer winner = null;
	private Integer eastPlayer = 0;
	
	public Integer getEastPlayer() {
		return eastPlayer;
	}

	public void setEastPlayer(Integer eastPlayer) {
		this.eastPlayer = eastPlayer;
	}

	public void setPlayerScore(int player, int score) {
		playerScores[player] = score;
	}
	
	public void setWinner(Integer player) {
		winner = player;
	}

    public Integer getWinner() {
        return winner;
    }

    public Integer[][] getResult() {
		Integer retval [][] = new Integer[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null}
				};
		
		// calculate the winner first
		if (winner != null) 
		{
	    	for (int x = 0; x < 4; x++)
    			if (playerScores[winner] != null && 
    				x != winner)
	    		{
	    			retval[x][winner] = -playerScores[winner];
	    			retval[winner][x] = playerScores[winner];
	    			if (x == eastPlayer || winner == eastPlayer) 
	    			{
	    				retval[x][winner] *= 2;
	    				retval[winner][x] *= 2;
	    			}
	    		}
			
		}
    	for (int x = 0; x < 4; x++)
    		for (int y = x + 1; y < 4; y++) {
    			if (winner != null && (x == winner || y == winner)) continue;
    			
    			if (playerScores[x] != null && playerScores[y] != null)
	    		{
	    			retval[x][y] = playerScores[x] - playerScores[y];
	    			retval[y][x] = playerScores[y] - playerScores[x];
	    			if (x == eastPlayer || y == eastPlayer) 
	    			{
	    				retval[x][y] *= 2;
	    				retval[y][x] *= 2;
	    			}
	    		}
    		}
		return retval;
	}

    public String getPlayerName(int index) {
        return "Player " + (index + 1);
    }
}
