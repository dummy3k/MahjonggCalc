package dummy.MahjonggCalc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
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
    public static final String EXTRA_ROUND_ID = "round_id";

	private static final String TAG = "AddScoreActivity";
    private RoundScoreCalculator calculator = new RoundScoreCalculator();
    private TextView[][] labelArray;
    private RadioButton winnerRadioButtons[];
    private RadioButton selectedWinnerRadioButton = null;
    private final List<TextView> amountLabelList = new ArrayList<TextView>();
    private Round round;

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

        winnerRadioButtons = new RadioButton[] {
                (RadioButton)findViewById(R.id.RadioButton01),
                (RadioButton)findViewById(R.id.RadioButton02),
                (RadioButton)findViewById(R.id.RadioButton03),
                (RadioButton)findViewById(R.id.RadioButton04),
                (RadioButton)findViewById(R.id.RadioButton05)
        };

        amountLabelList.add((TextView) findViewById(R.id.lblPoints1));
        amountLabelList.add((TextView) findViewById(R.id.lblPoints2));
        amountLabelList.add((TextView) findViewById(R.id.lblPoints3));
        amountLabelList.add((TextView) findViewById(R.id.lblPoints4));
        for (TextView item : amountLabelList) {
            item.setText("");
        }
        refreshResult();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        Long roundId = getIntent().getLongExtra(EXTRA_ROUND_ID, -1);
        if (roundId > 0) {
            Log.d(TAG, "loading round: " + roundId);
            round = roundService.withChildren(roundId);
        }   else {

            long[] players = (long[])getIntent().getLongArrayExtra(
                    AddScoreActivity.EXTRA_PLAYER_IDS);
            Round lastRound = roundService.lastRound(players, getResources());
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
            round.setTimeStamp(new Date());
        }

        int index = 0;
        for (PlayerRound playerRound : round.getPlayers()) {
            Person person = personService.findById(playerRound.getPersonId());
            nameLabels[index][0].setText(person.getName());
            windLabels[index].setText(playerRound.getWind().toString(getResources()));

            if (playerRound.getWind() == PlayerRound.windEnum.EAST) {
                calculator.setEastPlayer(index);
            }
            if (playerRound.getPersonId().equals(round.getWinner())) {
                winnerRadioButtons[index].setChecked(true);
                calculator.setWinner(index);
            }
            calculator.setPlayerScore(index, playerRound.getPoints());
            if (playerRound.getPoints() != null) {
                amountLabelList.get(index).setText(playerRound.getPoints().toString());
            }
            index++;
        }
        refreshResult();
    }

    public void onSetPointsClick(View view) {
    	Log.d(TAG, "onSetPointsClick");

        List<View> buttonList = new ArrayList<View>();
        buttonList.add(findViewById(R.id.cmdSet1));
        buttonList.add(findViewById(R.id.cmdSet2));
        buttonList.add(findViewById(R.id.cmdSet3));
        buttonList.add(findViewById(R.id.cmdSet4));
        final int playerIndex = buttonList.indexOf(view);

        Log.d(TAG, "playerIndex: " + playerIndex);
        if (round == null) {
            Log.w(TAG, "round is null");
        }
        if (round.getPlayers() == null) {
            Log.w(TAG, "round.getPlayers() is null");
        }
        Log.d(TAG, "round.getPlayers().size(): " + round.getPlayers().size());
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        PlayerRound playerRound = round.getPlayers().get(playerIndex);
        Person person = personService.findById(playerRound.getPersonId());
        alert.setMessage("Enter amount for " + person.getName());

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (playerRound.getPoints() != null) {
            input.setText(playerRound.getPoints().toString());
        }
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            Integer value;
            try{
                value = Integer.valueOf(input.getText().toString());
            } catch (NumberFormatException ex) {
                return;
            }
            Log.d(TAG, "value: " + value);
            amountLabelList.get(playerIndex).setText(value.toString());
            calculator.setPlayerScore(playerIndex, value);
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
    	for (RadioButton item : winnerRadioButtons) {
    		item.setChecked(item == view);
            if (item == view) {
                if (view == winnerRadioButtons[4]) {
                    calculator.setWinner(null);
                } else {
                    calculator.setWinner(winner);
                }
            }
            winner++;
    	}
        refreshResult();
        selectedWinnerRadioButton = (RadioButton)view;
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
        for (int index = 0; index < calculator.getPlayerCount(); index++) {
            if (calculator.getPlayerScore(index) == null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage(R.string.add_amount_each_player);
                alert.setPositiveButton(R.string.ok, null);
                alert.show();
                return;
            }
        }

        if (selectedWinnerRadioButton==null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.select_winner);
            alert.setPositiveButton(R.string.ok, null);
            alert.show();
            return;
        }

        Integer[][] result = calculator.getResult();
//        Round round = new Round();
//        round.setTimeStamp(new Date());

        int index = 0;
        for (PlayerRound playerRound : round.getPlayers()) {
//            PlayerRound playerRound = new PlayerRound();
//            playerRound.setPersonId(playerId);
//            playerRound.setRoundId(round.getId());
//            if (calculator == null) Log.w(TAG, "calculator is null");
//            if (calculator != null) {
//                int wind = (index + 4 - calculator.getEastPlayer()) % 4;
//                playerRound.setWind();
//                switch (wind) {
//                    case 0:
//                        playerRound.setWind(PlayerRound.windEnum.EAST);
//                        break;
//                    case 1:
//                        playerRound.setWind(PlayerRound.windEnum.SOUTH);
//                        break;
//                    case 2:
//                        playerRound.setWind(PlayerRound.windEnum.WEST);
//                        break;
//                    case 3:
//                        playerRound.setWind(PlayerRound.windEnum.NORTH);
//                        break;
//                    default:
//                        Log.w(TAG, "unknown wind");
//                }
//            }

            int sum = 0;
            for (int x = 0; x < 4; x++) {
                if (result[index][x] != null) {
                    sum += result[index][x];
                }
            }
            playerRound.setAmount(sum);
            playerRound.setPoints(calculator.getPlayerScore(index));

            if (calculator.getWinner() != null &&
                calculator.getWinner() == index) {

                round.setWinner(playerRound.getPersonId());
                roundService.saveOrUpdate(round);
            }
//            playerRoundService.saveOrUpdate(playerRound);
            index++;
        }
        roundService.saveOrUpdate(round);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		Log.w(TAG, "onCreateOptionsMenu()");
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.add_score_menu, menu);
    	return true;
    }

    /**
   	 * Called when menu item is selected
   	 */
   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
       	Log.d(TAG, "onOptionsItemSelected()");
   		switch (item.getItemId()) {
               case R.id.set_east_player:
                   item.getSubMenu().getItem(0).setTitle("hi there");
                   break;
               case R.id.player1:
                   Log.d(TAG, "player1");
                   break;
               case R.id.player2:
                   Log.d(TAG, "player2");
                   break;
//               MenuInflater inflater = getMenuInflater();
//               inflater.inflate(R.menu.set_east_player, item.getM);
   		default:
   			Log.d(TAG, "unknown menu");
   			return super.onOptionsItemSelected(item);
   		}
   		return true;
   	}

}
