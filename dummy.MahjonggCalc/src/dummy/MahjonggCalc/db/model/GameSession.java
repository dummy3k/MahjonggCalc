package dummy.MahjonggCalc.db.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Model {
	private Long id;
//	private List<LocationCell> locationCells;

    public GameSession() {
//        locationCells = new ArrayList<LocationCell>();
    }

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
}
