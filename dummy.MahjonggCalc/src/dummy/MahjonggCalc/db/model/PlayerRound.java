package dummy.MahjonggCalc.db.model;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;

import java.util.Date;

public class PlayerRound implements Model {
	private Long id;
    private Long game_session_id;
    private Long round_id;
    private Long person_id;
    private Integer amount;

	@Override
	public String toString() {
		return "PlayerRound()";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getGameSessionId() {
        return game_session_id;
    }

    public void setGameSessionId(Long game_session_id) {
        this.game_session_id = game_session_id;
    }

    public Long getRoundId() {
        return round_id;
    }

    public void setRoundId(Long round_id) {
        this.round_id = round_id;
    }

    public Long getPersonId() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
