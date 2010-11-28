package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.inject.Inject;
import dummy.MahjonggCalc.db.model.GameSession;
import dummy.MahjonggCalc.db.service.GameSessionService;

import java.util.List;

public class GameSessionServiceImpl extends AbstractServiceImpl<GameSession> implements GameSessionService {
//	@Inject private GameSessionCellService GameSessionCellService;

	@Override
	protected GameSession read(Cursor cursor) {
		GameSession obj = new GameSession();
		obj.setId(cursor.getLong(cursor.getColumnIndex("id")));
//		obj.setName(cursor.getString(cursor.getColumnIndex("name")));

//		List<GameSessionCell> cells = GameSessionCellService.findAllbyGameSession(obj);
//		obj.setGameSessionCells(cells);
		
		return obj;
	}
	
	private void _updateGameSessionCells(GameSession obj) {
//		if(obj.getGameSessionCells() != null) {
//			for(GameSessionCell GameSessionCell  : obj.getGameSessionCells()) {
//				GameSessionCellService.saveOrUpdate(GameSessionCell);
//			}
//		}
	}

	@Override
	public void save(GameSession obj) {
		super.save(obj);
		_updateGameSessionCells(obj);
	}
	
	@Override
	public void update(GameSession obj) {
		super.update(obj);
		_updateGameSessionCells(obj);
	}
	
	@Override
	protected ContentValues write(GameSession obj) {
		ContentValues values = new ContentValues();
		values.put("id", obj.getId());
//		values.put("name", obj.getName());
		
		return values;
	}

	@Override
	protected String getTableName() {
		return "GameSessions";
	}

}
