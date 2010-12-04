package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.service.PlayerRoundService;

import java.util.List;

public class PlayerRoundServiceImpl extends AbstractServiceImpl<PlayerRound> implements PlayerRoundService {
	@Override
	protected PlayerRound read(Cursor cursor) {
		PlayerRound obj = new PlayerRound();
		obj.setId(cursor.getLong(cursor.getColumnIndex("id")));
        obj.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
        obj.setGameSessionId(cursor.getLong(cursor.getColumnIndex("game_session_id")));
        obj.setPerson_id(cursor.getLong(cursor.getColumnIndex("person_id")));
        obj.setRoundId(cursor.getLong(cursor.getColumnIndex("round_id")));
		return obj;
	}

	@Override
	protected ContentValues write(PlayerRound obj) {
		ContentValues values = new ContentValues();
		values.put("id", obj.getId());
		values.put("amount", obj.getAmount());
		values.put("game_session_id", obj.getGameSessionId());
		values.put("person_id", obj.getPersonId());
		values.put("round_id", obj.getRoundId());
		return values;
	}

	@Override
	protected String getTableName() {
		return "player_rounds";
	}

    public List<PlayerRound> findAllByRoundId(Long roundId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(getTableName(), null,
                "round_id = ?", new String[]{roundId.toString()},
                null, null, null, null);

        List<PlayerRound> lst = _list(cursor);
        cursor.close();
        db.close();

        return lst;
    }
}
