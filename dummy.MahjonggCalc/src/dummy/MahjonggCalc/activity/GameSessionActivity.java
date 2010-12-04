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
import dummy.MahjonggCalc.db.model.GameSession;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.GameSessionService;
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
    private GameSessionService gameSessionService;

    @Inject
    private RoundService roundService;

    private GameSession gameSession;
    private Person[] players;
    private long[] personIds;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_activity);
        gameSession = new GameSession();
        gameSessionService.saveOrUpdate(gameSession);
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
        RoundListAdapter adapter = new RoundListAdapter(this,
                R.layout.game_session_listitem, R.id.TextView01,
                rounds.toArray(new Round[]{}));
        list.setAdapter(adapter);

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
//            super(context, resource);
            mContext = context;
            mItems = objects;
        }

        public View getView(int position, View ConvertView, ViewGroup parent) {
            Log.d(TAG, "getView(" + position + ")");
            LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater == null) {
                Log.e(TAG, "inflater is null!");
            }
			
            View row=inflater.inflate(R.layout.game_session_listitem, null);
            Round round = mItems[position];

            TextView label1=(TextView)row.findViewById(R.id.TextView01);
            label1.setText(round.findPlayerRoundByPlayerId(personIds[0]).getAmount().toString());

            TextView label2=(TextView)row.findViewById(R.id.TextView02);
            label2.setText(round.findPlayerRoundByPlayerId(personIds[1]).getAmount().toString());

            TextView label3=(TextView)row.findViewById(R.id.TextView03);
            label3.setText(round.findPlayerRoundByPlayerId(personIds[2]).getAmount().toString());

            TextView label4=(TextView)row.findViewById(R.id.TextView04);
            label4.setText(round.findPlayerRoundByPlayerId(personIds[3]).getAmount().toString());

//            Long person0id = round.findPlayerRoundByPlayerId(personIds[0]).getId();
//            Long person1id = round.findPlayerRoundByPlayerId(personIds[1]).getId();
//
//            Log.d(TAG, String.format("row %s, person0id %s", position, personIds[0]));
//            Log.d(TAG, String.format("row %s, person1id %s", position, personIds[1]));
//            Log.d(TAG, String.format("row %s, person0id %s", position, person0id));
//            Log.d(TAG, String.format("row %s, person1id %s", position, person1id));
//            label3.setText("333");
//            label4.setText("444");
            return row;
        }
    }
    
    public void onAddScoresClick(View view) {
    	Log.d(TAG, "onAddScoresClick");
        Intent intent = new Intent(this, AddScoreActivity.class);
        intent.putExtra(AddScoreActivity.EXTRA_SESSION_ID, gameSession.getId());

        long playerIds[] = new long[players.length];
        for (int index = 0; index < players.length; index++) {
            if (players[index] == null) {
                playerIds[index] = 0;
            } else {
                playerIds[index] = players[index].getId();
            }

        }
        intent.putExtra(AddScoreActivity.EXTRA_PLAYER_IDS, playerIds);

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
