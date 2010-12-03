package dummy.MahjonggCalc.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameSession implements Model {
	private Long id;
    private Date timeStamp;

	@Override
	public String toString() {
		return "GameSession()";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
