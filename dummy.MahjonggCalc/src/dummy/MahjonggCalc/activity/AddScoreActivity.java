package dummy.MahjonggCalc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.inject.Inject;
import dummy.MahjonggCalc.NextRoundLogic;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.RoundScoreCalculator;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PersonService;
import dummy.MahjonggCalc.db.service.PlayerRoundService;
import dummy.MahjonggCalc.db.service.RoundService;
import roboguice.activity.GuiceActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddScoreActivity extends GuiceActivity {
    public static final String EXTRA_PLAYER_IDS = "players";
    public static final String EXTRA_EAST_PLAYER_ID = "east_player_id";

	private static final String TAG = "AddScoreActivity";
    private RoundScoreCalculator calculator = new RoundScoreCalculator();
    private TextView[][] labelArray;
    private RadioButton eastRadioButtons[];

    @Inject
    private PlayerRoundService playerRoundService;

    @Inject
    private RoundService roundService;

    @Inject
    private PersonService personService;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score_activity);

        labelArray = new TextView[][] {
                {
                        (TextView)findViewById(R.id.lblResult00),
                        (TextView)findViewById(R.id.lblResult01),
                        (TextView)findViewById(R.id.lblResult02),
                        (TextView)findViewById(R.id.lblResult03),
                        (TextView)findViewById(R.id.lblResult04)
                },
                {
                        (TextView)findViewById(R.id.lblResult10),
                        (TextView)findViewById(R.id.lblResult11),
                        (TextView)findViewById(R.id.lblResult12),
                        (TextView)findViewById(R.id.lblResult13),
                        (TextView)findViewById(R.id.lblResult14)
                },
                {
                        (TextView)findViewById(R.id.lblResult20),
                        (TextView)findViewById(R.id.lblResult21),
                        (TextView)findViewById(R.id.lblResult22),
                        (TextView)findViewById(R.id.lblResult23),
                        (TextView)findViewById(R.id.lblResult24)
                },
                {
                        (TextView)findViewById(R.id.lblResult30),
                        (TextView)findViewById(R.id.lblResult31),
                        (TextView)findViewById(R.id.lblResult32),
                        (TextView)findViewById(R.id.lblResult33),
                        (TextView)findViewById(R.id.lblResult34)
                }
        };

        eastRadioButtons = new RadioButton[] {
                (RadioButton)findViewById(R.id.RadioButton01),
                (RadioButton)findViewById(R.id.RadioButton02),
                (RadioButton)findViewById(R.id.RadioButton03),
                (RadioButton)findViewById(R.id.RadioButton04),
                (RadioButton)findViewById(R.id.RadioButton05)
        };

        refreshResult();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long[] players = (long[])getIntent().getLongArrayExtra(
                AddScoreActivity.EXTRA_PLAYER_IDS);

        TextView[][] nameLabels = new TextView[][] {
                {(TextView)findViewById(R.id.lblPlayerName1_1)},
                {(TextView)findViewById(R.id.lblPlayerName2_1)},
                {(TextView)findViewById(R.id.lblPlayerName3_1)},
                {(TextView)findViewById(R.id.lblPlayerName4_1)}
        };

        TextView[] windLabels = new TextView[] {
                (TextView)findViewById(R.id.lblWind01),
                (TextView)findViewById(R.id.lblWind02),
                (TextView)findViewById(R.id.lblWind03),
                (TextView)findViewById(R.id.lblWind04),
        };

//        Long eastPlayer =(long)getIntent().getLongExtra(EXTRA_EAST_PLAYER_ID, Person.ID_NOBODY);
//        int eastPosition = -1;
//        for (int index = 0; index < players.length; index++) {
//            if (players[index] == eastPlayer) {
//                eastPosition = index;
//                calculator.setEastPlayer(index);
//                break;
//            }
//        }
//        if (eastPosition < 0) throw new RuntimeException("no east position");


        Round lastRound = roundService.lastRound(players, getResources());
        Round round = null;
        if (lastRound != null) {
            for (PlayerRound item : lastRound.getPlayers()) {
                Person person = personService.findById(item.getPersonId());
                Log.d(TAG, "wind: " + item.getWind("<null>") + ", person: " + person.getName());
            }
            NextRoundLogic roundLogic = new NextRoundLogic();
            round = roundLogic.next(lastRound);
        } else {
            round = roundService.newRound(players);
        }

        for (int index = 0; index < players.length; index++) {
            PlayerRound playerRound = round.getPlayers().get(index);
            Person person = personService.findById(players[index]);
            nameLabels[index][0].setText(person.getName());
            windLabels[index].setText(playerRound.getWind().toString(getResources()));

            if (playerRound.getWind() == PlayerRound.windEnum.EAST) {
                calculator.setEastPlayer(index);
            }

//            int wind = (index - eastPosition + 4) % 4;
//            switch (wind) {
//                case 0:
//                    windLabels[index].setText(R.string.east);
//                    break;
//                case 1:
//                    windLabels[index].setText(R.string.south);
//                    break;
//                case 2:
//                    windLabels[index].setText(R.string.west);
//                    break;
//                case 3:
//                    windLabels[index].setText(R.string.north);
//                    break;
//            }
        }
    }

    public void onSetPointsClick(View view) {
    	Log.d(TAG, "onSetPointsClick");

        List<View> buttonList = new ArrayList<View>();
        buttonList.add(findViewById(R.id.cmdSet1));
        buttonList.add(findViewById(R.id.cmdSet2));
        buttonList.add(findViewById(R.id.cmdSet3));
        buttonList.add(findViewById(R.id.cmdSet4));
        final int player = buttonList.indexOf(view);

        final List<TextView> labelList = new ArrayList<TextView>();
        labelList.add((TextView) findViewById(R.id.lblPoints1));
        labelList.add((TextView) findViewById(R.id.lblPoints2));
        labelList.add((TextView) findViewById(R.id.lblPoints3));
        labelList.add((TextView) findViewById(R.id.lblPoints4));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

//        alert.setTitle("Title");
        alert.setMessage("Enter amount for " + calculator.getPlayerName(player));

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          String value = input.getText().toString();
            Log.d(TAG, "value: " + value);
            labelList.get(player).setText(value);
            calculator.setPlayerScore(player, Integer.valueOf(value));
            refreshResult();
          }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
          }
        });

        alert.show();

    }

    public void onWinnerSelect(View view) {
    	Log.d(TAG, "onWinnerSelect");
        Integer winner = 0;
    	for (RadioButton item : eastRadioButtons) {
    		item.setChecked(item == view);
            if (item == view) {
                if (view == eastRadioButtons[4]) {
                    calculator.setWinner(null);
                } else {
                    calculator.setWinner(winner);
                }
            }
            winner++;
    	}
        refreshResult();
    }

    private void refreshResult() {
        Integer[][] result = calculator.getResult();

        for (int x = 0; x < 4; x++) {
            Integer sum = 0;
            for (int y = 0; y < 4; y++) {
                if (result[x][y] == null) {
                    labelArray[x][y].setText("");
                } else {
                    sum += result[x][y];
                    ActivityTools.setLabel(labelArray[x][y], result[x][y]);
                }
            }
            ActivityTools.setLabel(labelArray[x][4], sum);
        }
    }

    public void onSaveClick(View view) {
        Integer[][] result = calculator.getResult();
        Round round = new Round();
        round.setTime_stamp(new Date());
        roundService.saveOrUpdate(round);

        long[] players = (long[])getIntent().getLongArrayExtra(
                AddScoreActivity.EXTRA_PLAYER_IDS);
        int index = 0;
        for (long playerId : players) {
            PlayerRound playerRound = new PlayerRound();
            playerRound.setPersonId(playerId);
            playerRound.setRoundId(round.getId());
            if (calculator == null) Log.w(TAG, "calculator is null");
            if (calculator != null && calculator.getWinner() != null) {
                int wind = (index + 4 - calculator.getEastPlayer()) % 4;
                switch (wind) {
                    case 0:
                        playerRound.setWind(PlayerRound.windEnum.EAST);
                        break;
                    case 1:
                        playerRound.setWind(PlayerRound.windEnum.SOUTH);
                        break;
                    case 2:
                        playerRound.setWind(PlayerRound.windEnum.WEST);
                        break;
                    case 3:
                        playerRound.setWind(PlayerRound.windEnum.NORTH);
                        break;
                    default:
                        Log.w(TAG, "unknown wind");
                }
            }
            roundService.saveOrUpdate(round);

            int sum = 0;
            for (int x = 0; x < 4; x++) {
                if (result[index][x] != null) {
                    sum += result[index][x];
                }
            }
            playerRound.setAmount(sum);

            if (calculator.getWinner() != null &&
                    calculator.getWinner() == index) {
                round.setWinner(playerId);
            }
            playerRoundService.saveOrUpdate(playerRound);
            index++;
        }

        finish();
    }
}
