package dummy.MahjonggCalc.db.service;

import dummy.MahjonggCalc.db.model.Round;

import java.util.List;

public interface RoundService extends Service<Round> {
    List<Round> findAllByGameSessionId(Long id);
}
