package dummy.MahjonggCalc.db.service;

import android.content.Context;
import android.content.res.Resources;
import dummy.MahjonggCalc.db.model.Round;

import java.util.List;

public interface RoundService extends Service<Round> {
    List<Round> findAllByGameSessionId(Long id);
    List<Round> findAllByPlayers(long[] ids, Resources resources);
}
