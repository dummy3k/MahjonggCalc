package dummy.MahjonggCalc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.inject.Inject;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.PlayerRound;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PersonService;
import dummy.MahjonggCalc.db.service.RoundService;
import roboguice.activity.GuiceActivity;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.List;

public class GameSessionActivity extends GuiceActivity {
	private static final String TAG = "GameSessionActivity";
    public static final String EXTRA_PARTICIPANT_1 = "participant_1";
    public static final String EXTRA_PARTICIPANT_2 = "participant_2";
    public static final String EXTRA_PARTICIPANT_3 = "participant_3";
    public static final String EXTRA_PARTICIPANT_4 = "participant_4";

    @InjectView(R.id.ListView01)
    ListView list;

    @Inject
    private PersonService personService;

    @Inject
    private RoundService roundService;

    private Person[] players;
    private long[] personIds;
    private Long eastPlayerId;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_activity);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        Intent intent = getIntent();
        personIds = new long[] {
                intent.getLongExtra(EXTRA_PARTICIPANT_1, Person.ID_NOBODY),
                intent.getLongExtra(EXTRA_PARTICIPANT_2, Person.ID_NOBODY),
                intent.getLongExtra(EXTRA_PARTICIPANT_3, Person.ID_NOBODY),
                intent.getLongExtra(EXTRA_PARTICIPANT_4, Person.ID_NOBODY),
        };
        List<Round> rounds = roundService.findAllByPlayers(personIds, getResources());
        Round[] roundsArray = rounds.toArray(new Round[]{});
        RoundListAdapter adapter = new RoundListAdapter(this,
                R.layout.game_session_listitem, R.id.TextView01,
                roundsArray);
        list.setAdapter(adapter);

        if (roundsArray.length > 0) {
            Round lastRound = roundsArray[roundsArray.length -1];
            Long eastPlayerId = lastRound.findPlayerIdRoundByWind(
                    PlayerRound.windEnum.EAST);
            if (lastRound.getWinner().equals(eastPlayerId)) {
                this.eastPlayerId = eastPlayerId;
            } else {
                this.eastPlayerId = lastRound.findPlayerIdRoundByWind(
                        PlayerRound.windEnum.SOUTH);
            }
        }

        int sums[] = new int[4];
        for (Round item : rounds) {
            sums[0] += item.findPlayerRoundByPlayerId(personIds[0]).getAmount();
            sums[1] += item.findPlayerRoundByPlayerId(personIds[1]).getAmount();
            sums[2] += item.findPlayerRoundByPlayerId(personIds[2]).getAmount();
            sums[3] += item.findPlayerRoundByPlayerId(personIds[3]).getAmount();
        }
        View summaryView = findViewById(R.id.summaryListItem);
        ActivityTools.setLabel((TextView)summaryView.findViewById(
                R.id.TextView01), sums[0]);
        ActivityTools.setLabel((TextView)summaryView.findViewById(
                R.id.TextView02), sums[1]);
        ActivityTools.setLabel((TextView)summaryView.findViewById(
                R.id.TextView03), sums[2]);
        ActivityTools.setLabel((TextView)summaryView.findViewById(
                R.id.TextView04), sums[3]);

        setAvatar(R.id.player1, EXTRA_PARTICIPANT_1);
        setAvatar(R.id.player2, EXTRA_PARTICIPANT_2);
        setAvatar(R.id.player3, EXTRA_PARTICIPANT_3);
        setAvatar(R.id.player4, EXTRA_PARTICIPANT_4);

        players = new Person[] {
            personService.findById(intent.getLongExtra(EXTRA_PARTICIPANT_1, Person.ID_NOBODY)),
            personService.findById(intent.getLongExtra(EXTRA_PARTICIPANT_2, Person.ID_NOBODY)),
            personService.findById(intent.getLongExtra(EXTRA_PARTICIPANT_3, Person.ID_NOBODY)),
            personService.findById(intent.getLongExtra(EXTRA_PARTICIPANT_4, Person.ID_NOBODY)),
        };
    }

    private void setAvatar(int layout, String extra) {
        Long personId = getIntent().getLongExtra(extra, 0);
        if (personId <= 0) return;

        Person person = personService.findById(personId);
        ImageView imageView = (ImageView)findViewById(layout).findViewById(R.id.ImageView01);
        imageView.setImageBitmap(person.getImage());

        TextView textView = (TextView)findViewById(layout).findViewById(R.id.txtName1);
        textView.setText(person.getName());
    }

    public class RoundListAdapter extends ArrayAdapter<Round> {
        private Context mContext;
        private Round[] mItems;

        public RoundListAdapter(Context context, int resource,
                                int textViewResourceId, Round[] objects) {
			
            super(context, resource, textViewResourceId, objects);
            mContext = context;
            mItems = objects;
        }

        public View getView(int position, View ConvertView, ViewGroup parent) {
            Log.d(TAG, "getView(" + position + ")");
            LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Round round = mItems[position];
            View row = inflater.inflate(R.layout.game_session_listitem, null);

            PlayerRound playerRound = round.findPlayerRoundByPlayerId(personIds[0]);
            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView01),
                    String.format("%s (%s)",
                            playerRound.getAmount("-"),
                            playerRound.getWind("-").charAt(0)),
                    playerRound.getAmount());

            playerRound = round.findPlayerRoundByPlayerId(personIds[1]);
            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView02),
                    String.format("%s (%s)",
                            playerRound.getAmount("-"),
                            playerRound.getWind("-").charAt(0)),
                    playerRound.getAmount());

            playerRound = round.findPlayerRoundByPlayerId(personIds[2]);
            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView03),
                    String.format("%s (%s)",
                            playerRound.getAmount("-"),
                            playerRound.getWind("-").charAt(0)),
                    playerRound.getAmount());

            playerRound = round.findPlayerRoundByPlayerId(personIds[3]);
            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView04),
                    String.format("%s (%s)",
                            playerRound.getAmount("-"),
                            playerRound.getWind("-").charAt(0)),
                    playerRound.getAmount());

//            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView01),
//                    round.findPlayerRoundByPlayerId(personIds[0]).getAmount());
//
//            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView02),
//                    round.findPlayerRoundByPlayerId(personIds[1]).getAmount());
//
//            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView03),
//                    round.findPlayerRoundByPlayerId(personIds[2]).getAmount());
//
//            ActivityTools.setLabel((TextView)row.findViewById(R.id.TextView04),
//                    round.findPlayerRoundByPlayerId(personIds[3]).getAmount());

            return row;
        }

        private void setLabel(int layoutId, int index, View row) {

        }
    }
    
    public void onAddScoresClick(View view) {
    	Log.d(TAG, "onAddScoresClick");
        Intent intent = new Intent(this, AddScoreActivity.class);

        long playerIds[] = new long[players.length];
        for (int index = 0; index < players.length; index++) {
            if (players[index] == null) {
                playerIds[index] = 0;
            } else {
                playerIds[index] = players[index].getId();
            }

        }
        intent.putExtra(AddScoreActivity.EXTRA_PLAYER_IDS, playerIds);
        intent.putExtra(AddScoreActivity.EXTRA_EAST_PLAYER_ID, eastPlayerId);

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
