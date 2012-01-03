package dummy.MahjonggCalc.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import dummy.MahjonggCalc.R;
import dummy.MahjonggCalc.db.model.Round;
import roboguice.activity.GuiceActivity;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.List;

public class PreGameActivity extends GuiceActivity {
    private static final String TAG = "PreGameActivity";

    @InjectView(R.id.ListView01)
    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_game);

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        List<String> myList = new ArrayList<String>();
        myList.add("(East) John");
        myList.add("(South) John");
        myList.add("(West) John");
        myList.add("(North) John");
        myList.add("John");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                PreGameActivity.this,
                android.R.layout.simple_list_item_1,
                myList);
        list.setAdapter(adapter);
    }

    public void onAddPerson(View view) throws Exception  {
        this.toast("Hi there!");
    }

    private void toast(Exception ex) {
        Log.e(TAG, ex.toString());
        this.toast(ex.toString());
    }

    private void toast(String s) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast.makeText(context, s, duration).show();
    }

}