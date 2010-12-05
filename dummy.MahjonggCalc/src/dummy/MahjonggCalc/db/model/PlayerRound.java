package dummy.MahjonggCalc.db.model;

public class PlayerRound implements Model {
	private Long id;
    private Long roundId;
    private Long person_id;
    private Integer amount;

    public enum windEnum {
        EAST,
        SOUTH,
        WEST,
        NORTH
    }
    private windEnum wind;

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

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long round_id) {
        this.roundId = round_id;
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

    public String getAmount(String returnIfNull) {
        if (amount == null) {
            return returnIfNull;
        }
        return amount.toString();
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public windEnum getWind() {
        return wind;
    }

    public String getWind(String returnIfNull) {
        if (wind == null) {
            return returnIfNull;
        }
        return wind.toString();
    }

    public void setWind(windEnum wind) {
        this.wind = wind;
    }

}
