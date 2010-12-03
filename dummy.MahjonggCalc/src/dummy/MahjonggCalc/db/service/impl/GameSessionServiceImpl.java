package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.inject.Inject;
import dummy.MahjonggCalc.db.model.GameSession;
import dummy.MahjonggCalc.db.service.GameSessionService;

import java.util.List;

public class GameSessionServiceImpl extends AbstractServiceImpl<GameSession> implements GameSessionService {
	@Override
	protected GameSession read(Cursor cursor) {
		GameSession obj = new GameSession();
		obj.setId(cursor.getLong(cursor.getColumnIndex("id")));
        obj.setTimeStamp(parseDate(cursor, "time_stamp"));
		return obj;
	}

	@Override
	protected ContentValues write(GameSession obj) {
		ContentValues values = new ContentValues();
		values.put("id", obj.getId());
		values.put("time_stamp", formatDate(obj.getTimeStamp()));
		return values;
	}

	@Override
	protected String getTableName() {
		return "game_sessions";
	}

}
