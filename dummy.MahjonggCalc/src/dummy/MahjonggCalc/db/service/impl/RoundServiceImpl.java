package dummy.MahjonggCalc.db.service.impl;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.inject.Inject;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PlayerRoundService;
import dummy.MahjonggCalc.db.service.RoundService;

import java.util.ArrayList;
import java.util.List;

public class RoundServiceImpl extends AbstractServiceImpl<Round> implements RoundService {
    private static final String TAG = "RoundServiceImpl";

	@Inject private PlayerRoundService PlayerRoundService;

	@Override
	protected Round read(Cursor cursor) {
		Round obj = new Round();
		obj.setId(cursor.getLong(cursor.getColumnIndex("id")));
		obj.setTime_stamp(parseDate(cursor, "time_stamp"));
        obj.setWinner(cursor.getLong(cursor.getColumnIndex("winner")));

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
		values.put("winner", obj.getWinner());
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
                Round round = loadWithChildren(cursor, ids);
                lst.add(round);
            } while (cursor.moveToNext());
        }


        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        db.close();
        return lst;
    }

    private Round loadWithChildren(Cursor cursor, long[] ids) {
        final int colsPerPlayer = 3;
        Round round = new Round();
        round.setId(cursor.getLong(0));
        round.setWinner(cursor.getLong(1));
        Log.d(TAG, "winner is: " + round.getWinner());

        for (int index = 0; index < 4; index++) {
            PlayerRound playerRound1 = new PlayerRound();
            playerRound1.setPersonId(ids[index]);
            playerRound1.setRoundId(cursor.getLong(0));
            playerRound1.setId(cursor.getLong(index * colsPerPlayer + 2));
            playerRound1.setAmount(cursor.getInt(index * colsPerPlayer + 3));

            String wind = cursor.getString(index * colsPerPlayer + 4);
            if (wind == null) {
                Log.w(TAG, "wind is null, player " + index);
                playerRound1.setWind(null);
            } else {
                playerRound1.setWind(PlayerRound.windEnum.valueOf(
                        wind));
            }
            round.getPlayers().add(playerRound1);
        }
        return round;
    }

    public Round lastRound(long[] ids, Resources resources) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String sql = resources.getString(R.string.select_last_round_by_players);
        String params[] = new String[] {
            Long.toString(ids[0]),
            Long.toString(ids[1]),
            Long.toString(ids[2]),
            Long.toString(ids[3])
        };

        Round retVal = null;
        Cursor cursor = db.rawQuery(sql, params);
        if (cursor != null && cursor.moveToFirst()) {
            retVal = loadWithChildren(cursor, ids);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        db.close();
        return retVal;
    }

    public Round newRound(long[] ids) {
        Round retVal = new Round();
        List<PlayerRound> players = new ArrayList<PlayerRound>();
        for (int index = 0; index < ids.length; index++) {
            PlayerRound playerRound = new PlayerRound();
            playerRound.setPersonId(ids[index]);
            playerRound.setWind(PlayerRound.windEnum.byInt(index));
            players.add(playerRound);
        }
        retVal.setPlayers(players);
        return retVal;
    }
}
