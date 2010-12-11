package dummy.MahjonggCalc;

import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.*;

public class NextRoundLogicTest extends TestCase {
    public void testEastWins() {
        Round round = new Round();
        round.setPlayers(Arrays.asList(
                new PlayerRound(null, 0L, PlayerRound.windEnum.EAST),
                new PlayerRound(null, 1L, PlayerRound.windEnum.SOUTH),
                new PlayerRound(null, 2L, PlayerRound.windEnum.WEST),
                new PlayerRound(null, 3L, PlayerRound.windEnum.NORTH)
        ));
        round.setWinner(0L);
        Assert.assertEquals(new Long(0),
                round.findPlayerIdRoundByWind(PlayerRound.windEnum.EAST));

        NextRoundLogic logic = new NextRoundLogic();
        Round nextRound = logic.next(round);
        Assert.assertEquals(nextRound.getPlayers().get(0).getWind(),
                PlayerRound.windEnum.EAST);
      }

    public void testEastLost() {
        Round round = new Round();
        round.setPlayers(Arrays.asList(
                new PlayerRound(100L, 0L, PlayerRound.windEnum.EAST),
                new PlayerRound(101L, 1L, PlayerRound.windEnum.SOUTH),
                new PlayerRound(102L, 2L, PlayerRound.windEnum.WEST),
                new PlayerRound(103L, 3L, PlayerRound.windEnum.NORTH)
        ));
        round.setWinner(1L);

        NextRoundLogic logic = new NextRoundLogic();
        Round nextRound = logic.next(round);
        Assert.assertEquals(PlayerRound.windEnum.EAST,
                round.getPlayers().get(0).getWind());
        Assert.assertEquals(PlayerRound.windEnum.NORTH,
                nextRound.getPlayers().get(0).getWind());
        Assert.assertEquals(new Long(100L),
                round.getPlayers().get(0).getId());
        Assert.assertEquals(null,
                nextRound.getPlayers().get(0).getId());
      }

    public void testNoWinner() {
        Round round = new Round();
        round.setPlayers(Arrays.asList(
                new PlayerRound(100L, 0L, PlayerRound.windEnum.EAST),
                new PlayerRound(101L, 1L, PlayerRound.windEnum.SOUTH),
                new PlayerRound(102L, 2L, PlayerRound.windEnum.WEST),
                new PlayerRound(103L, 3L, PlayerRound.windEnum.NORTH)
        ));
//        round.setWinner(1L);

        NextRoundLogic logic = new NextRoundLogic();
        Round nextRound = logic.next(round);
        Assert.assertEquals(PlayerRound.windEnum.EAST,
                round.getPlayers().get(0).getWind());
        Assert.assertEquals(PlayerRound.windEnum.EAST,
                nextRound.getPlayers().get(0).getWind());
        Assert.assertEquals(new Long(100L),
                round.getPlayers().get(0).getId());
        Assert.assertEquals(null,
                nextRound.getPlayers().get(0).getId());
      }

}
