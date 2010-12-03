package dummy.MahjonggCalc.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.inject.Inject;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.model.Round;
import dummy.MahjonggCalc.db.service.PersonService;
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

        List<Round> rounds = new ArrayList<Round>();
        rounds.add(new Round());
        rounds.add(new Round());
        rounds.add(new Round());
        rounds.add(new Round());
        RoundListAdapter adapter = new RoundListAdapter(this,
                R.layout.game_session_listitem, R.id.TextView01,
                rounds.toArray(new Round[]{}));
        list.setAdapter(adapter);

        setAvatar(R.id.player1, EXTRA_PARTICIPANT_1);
        setAvatar(R.id.player2, EXTRA_PARTICIPANT_2);
        setAvatar(R.id.player3, EXTRA_PARTICIPANT_3);
        setAvatar(R.id.player4, EXTRA_PARTICIPANT_4);
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
            Round p = mItems[position];
            TextView label=(TextView)row.findViewById(R.id.TextView01);
            label.setText("First");


//            if (UNKNOWN.equals(text)) {
//                label.setTextColor(mContext.getResources().getColor(R.color.white));
//            } else {
//                label.setTextColor(mContext.getResources().getColor(R.color.green));
//            }

            return row;
        }
    }
    
    public void onSetPointsClick(View view) {
    	Log.d(TAG, "onSetPointsClick");
    }
}
