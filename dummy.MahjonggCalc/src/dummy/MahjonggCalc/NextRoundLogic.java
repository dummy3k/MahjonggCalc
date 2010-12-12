package dummy.MahjonggCalc;

import android.util.Log;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;

public class NextRoundLogic {
    private static final String TAG = "NextRoundLogic";

    public Round next(Round previous) {
        Long eastPlayer = previous.findPlayerIdRoundByWind(
                PlayerRound.windEnum.EAST);
        if (eastPlayer == null) throw new RuntimeException("no east");
//        if (previous.getWinner() == null) throw new RuntimeException("no winner");

        Round retVal = null;
        try {
            retVal = previous.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("this should not happen");
        }
        retVal.setId(null);
        for (PlayerRound item : retVal.getPlayers()) {
            item.setId(null);
        }

        if (previous.getWinner() == null ||
                previous.getWinner() == Person.ID_NOBODY ||
                eastPlayer.equals(previous.getWinner())) {
            return retVal;
        }
        Log.d(TAG, "east lost");
        for (PlayerRound item : retVal.getPlayers()) {
            switch (item.getWind()) {
                case EAST:
                    item.setWind(PlayerRound.windEnum.NORTH);
                    break;
                case SOUTH:
                    item.setWind(PlayerRound.windEnum.EAST);
                    break;
                case WEST:
                    item.setWind(PlayerRound.windEnum.SOUTH);
                    break;
                case NORTH:
                    item.setWind(PlayerRound.windEnum.WEST);
                    break;
            }
        }

        return retVal;
    }
}
