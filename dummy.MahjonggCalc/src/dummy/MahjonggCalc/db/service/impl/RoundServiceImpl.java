package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.google.inject.Inject;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PlayerRoundService;
import dummy.MahjonggCalc.db.service.RoundService;

import java.io.StringWriter;
import java.util.ArrayList;
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

    public List<Round> findAllByPlayers(long[] ids, Resources resources) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String sql = resources.getString(R.string.select_rounds_by_players);
        String params[] = new String[] {
            Long.toString(ids[0]),
            Long.toString(ids[1]),
            Long.toString(ids[2]),
            Long.toString(ids[3])
        };
        Cursor cursor = db.rawQuery(sql, params);

//        List<Round> lst = _list(cursor);
        List<Round> lst = new ArrayList<Round>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Round round = new Round();
                round.setId(cursor.getLong(0));

                PlayerRound playerRound1 = new PlayerRound();
                playerRound1.setRoundId(cursor.getLong(0));
                playerRound1.setId(cursor.getLong(1));
                playerRound1.setAmount(cursor.getInt(2));
                round.getPlayers().add(playerRound1);

                PlayerRound playerRound2 = new PlayerRound();
                playerRound2.setRoundId(cursor.getLong(0));
                playerRound2.setId(cursor.getLong(3));
                playerRound2.setAmount(cursor.getInt(4));
                round.getPlayers().add(playerRound2);

                PlayerRound playerRound3 = new PlayerRound();
                playerRound3.setRoundId(cursor.getLong(0));
                playerRound3.setId(cursor.getLong(5));
                playerRound3.setAmount(cursor.getInt(6));
                round.getPlayers().add(playerRound3);

                PlayerRound playerRound4 = new PlayerRound();
                playerRound4.setRoundId(cursor.getLong(0));
                playerRound4.setId(cursor.getLong(7));
                playerRound4.setAmount(cursor.getInt(8));
                round.getPlayers().add(playerRound4);

                lst.add(round);
            } while (cursor.moveToNext());
        }


        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        db.close();
        return lst;
    }
}
