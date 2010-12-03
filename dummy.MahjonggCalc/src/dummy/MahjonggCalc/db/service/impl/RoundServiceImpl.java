package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.google.inject.Inject;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PlayerRoundService;
import dummy.MahjonggCalc.db.service.RoundService;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundServiceImpl extends AbstractServiceImpl<Round> implements RoundService {
	@Inject private PlayerRoundService PlayerRoundService;

	@Override
	protected Round read(Cursor cursor) {
		Round obj = new Round();
		obj.setId(cursor.getLong(cursor.getColumnIndex("id")));
		obj.setGameSessionId(cursor.getLong(cursor.getColumnIndex("game_session_id")));
		obj.setTime_stamp(parseDate(cursor, "time_stamp"));

		List<PlayerRound> cells = PlayerRoundService.findAllByRoundId(obj.getId());
		obj.setPlayers(cells);
		
		return obj;
	}
	
	private void __updatePlayers__(Round obj) {
		if(obj.getPlayers() != null) {
			for(PlayerRound PlayerRound  : obj.getPlayers()) {
				PlayerRoundService.saveOrUpdate(PlayerRound);
			}
		}
	}

	@Override
	public void save(Round obj) {
		super.save(obj);
		__updatePlayers__(obj);
	}
	
	@Override
	public void update(Round obj) {
		super.update(obj);
		__updatePlayers__(obj);
	}
	
	@Override
	protected ContentValues write(Round obj) {
		ContentValues values = new ContentValues();
		values.put("id", obj.getId());
		values.put("game_session_id", obj.getGameSessionId());
		values.put("time_stamp", formatDate(obj.getTime_stamp()));
		return values;
	}

	@Override
	protected String getTableName() {
		return "rounds";
	}

    public List<Round> findAllByGameSessionId(Long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(getTableName(), null,
                "game_session_id = ?", new String[]{id.toString()},
                null, null, null, null);

        List<Round> lst = _list(cursor);
        db.close();
        return lst;

    }
}
