package dummy.MahjonggCalc.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Round implements Model {
	private Long id;
	private List<PlayerRound> players;
    private Date time_stamp;

    public Round() {
        players = new ArrayList<PlayerRound>();
    }

	@Override
	public String toString() {
		return "Round()";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public PlayerRound findPlayerRoundByPlayerId(long id) {
        for (PlayerRound item : players) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new RuntimeException("player not found");
    }

    public List<PlayerRound> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerRound> players) {
        this.players = players;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }
}
