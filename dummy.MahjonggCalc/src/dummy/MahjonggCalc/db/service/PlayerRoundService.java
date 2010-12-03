package dummy.MahjonggCalc.db.service;

import dummy.MahjonggCalc.db.model.PlayerRound;

import java.util.List;

public interface PlayerRoundService extends Service<PlayerRound> {
    List<PlayerRound> findAllByRoundId(Long roundId);
}
