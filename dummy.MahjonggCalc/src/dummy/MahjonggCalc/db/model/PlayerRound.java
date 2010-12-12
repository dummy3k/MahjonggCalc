package dummy.MahjonggCalc.db.model;

import android.content.res.Resources;
import dummy.MahjonggCalc.R;

public class PlayerRound implements Model, Cloneable {
	private Long id;
    private Long roundId;
    private Long personId;
    private Integer amount;
    private Integer points;

    public enum windEnum {
        EAST(0),
        SOUTH(1),
        WEST(2),
        NORTH(3);

        private final int value;
        windEnum(int i) {
            value = i;
        }

        public static windEnum byInt(int value) {
            if (value == EAST.value) return  EAST;
            if (value == SOUTH.value) return  SOUTH;
            if (value == WEST.value) return  WEST;
            if (value == NORTH.value) return  NORTH;
            throw new RuntimeException("byInt, bad value");
        }

        public String toString(Resources res) {
            switch (this) {
                case EAST:
                    return res.getString(R.string.east);
                case SOUTH:
                    return res.getString(R.string.south);
                case WEST:
                    return res.getString(R.string.west);
                case NORTH:
                    return res.getString(R.string.north);
            }
            throw new RuntimeException("wtf");
        }
    }
    private windEnum wind;

    public  PlayerRound() {

    }

    public  PlayerRound(Long id, Long person_id, windEnum wind) {
        this.id = id;
        this.personId = person_id;
        this.wind = wind;
    }

    public PlayerRound clone() throws CloneNotSupportedException {
        return (PlayerRound)super.clone();
    }


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
        return personId;
    }

    public void setPersonId(Long person_id) {
        this.personId = person_id;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
