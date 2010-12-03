package dummy.MahjonggCalc.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.GameSession;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.service.PersonService;
import roboguice.activity.GuiceActivity;

import java.util.ArrayList;
import java.util.List;

public class StartGameSessionActivity extends GuiceActivity {
    private static final String TAG = "StartGameSessionActivity";
    private static final int REQUEST_PICK_CONTACT = 1;
    private Integer currentPlayer = null;
    private List<ImageView> imageViews;
    private List<TextView> textViews;
    private long participants[] = null;

    @Inject
    private PersonService personService;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_session_activity);

        imageViews = new ArrayList<ImageView>();
//        imageViews.add((ImageView)findViewById(R.id.ImageView01));
        imageViews.add((ImageView) findViewById(R.id.player1).findViewById(R.id.ImageView01));
        imageViews.add((ImageView) findViewById(R.id.player2).findViewById(R.id.ImageView01));
        imageViews.add((ImageView) findViewById(R.id.player3).findViewById(R.id.ImageView01));
        imageViews.add((ImageView) findViewById(R.id.player4).findViewById(R.id.ImageView01));

        textViews = new ArrayList<TextView>();
        textViews.add((TextView) findViewById(R.id.player1).findViewById(R.id.txtName1));
        textViews.add((TextView) findViewById(R.id.player2).findViewById(R.id.txtName1));
        textViews.add((TextView) findViewById(R.id.player3).findViewById(R.id.txtName1));
        textViews.add((TextView) findViewById(R.id.player4).findViewById(R.id.txtName1));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        if (participants == null) {
            Log.d(TAG, "onResume(), participants == null");
            participants = new long[4];
            SharedPreferences preferences = getSharedPreferences(TAG, MODE_PRIVATE);
            setPlayer(0, preferences.getLong("player1", Person.ID_NOBODY));
            setPlayer(1, preferences.getLong("player2", Person.ID_NOBODY));
            setPlayer(2, preferences.getLong("player3", Person.ID_NOBODY));
            setPlayer(3, preferences.getLong("player4", Person.ID_NOBODY));
        }

    }

    private void setPlayer(int player, Long id) {
        Person person = personService.findById(id);
        if (person == null) {
            imageViews.get(player).setImageBitmap(
                    BitmapFactory.decodeResource(getResources(),
                            R.drawable.icon_round_question_mark));
            textViews.get(player).setText(R.string.nobody);
            participants[player] = Person.ID_NOBODY;
        } else {
            imageViews.get(player).setImageBitmap(person.getImage());
            textViews.get(player).setText(person.getName());
            participants[player] = id;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        Log.d(TAG, "savePreferences, player1: " + participants[0]);
        editor.putLong("player1", participants[0]);
        editor.putLong("player2", participants[1]);
        editor.putLong("player3", participants[2]);
        editor.putLong("player4", participants[3]);
        editor.commit();
    }

    private int playerByView(View view) {
        switch (view.getId()) {
            case R.id.player1:
                return 0;
            case R.id.player2:
                return 1;
            case R.id.player3:
                return 2;
            case R.id.player4:
                return 3;
            default:
                return playerByView((View)view.getParent());
        }
    }

    public void onStartGameClick(View view) {
        Intent intent = new Intent(this, GameSessionActivity.class);
        intent.putExtra(GameSessionActivity.EXTRA_PARTICIPANT_1, participants[0]);
        intent.putExtra(GameSessionActivity.EXTRA_PARTICIPANT_2, participants[1]);
        intent.putExtra(GameSessionActivity.EXTRA_PARTICIPANT_3, participants[2]);
        intent.putExtra(GameSessionActivity.EXTRA_PARTICIPANT_4, participants[3]);
        startActivity(intent);
    }

    public void onSelectPersonClick(View view) {
        Log.d(TAG, "onSelectPerson");
        currentPlayer = playerByView(view);
        Log.d(TAG, "currentPlayer: " + currentPlayer);
//        Person.startPickActivityForResult(this, REQUEST_PICK_CONTACT);
        personService.startPickActivityForResult(REQUEST_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Log.d(TAG, "onActivityResult()");

        switch (reqCode) {
            case (REQUEST_PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Person person = personService.fromActivityResult(data);
                    Log.d(TAG, "Person: " + person.getName());
                    setPlayer(currentPlayer, person.getId());
                    savePreferences();
                }
                break;
        }
    }

}