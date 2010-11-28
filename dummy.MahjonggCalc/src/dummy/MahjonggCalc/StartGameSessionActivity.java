package dummy.MahjonggCalc;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
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
                    imageViews.get(currentPlayer).setImageBitmap(person.getImage());
                    textViews.get(currentPlayer).setText(person.getName());
                }
                break;
        }
    }

}