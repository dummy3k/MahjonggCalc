package dummy.MahjonggCalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AddScoreActivity extends Activity {
	private static final String TAG = "AddScoreActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score_activity);
    }

    public void onLabelClick(View view) {
    	Log.d(TAG, "onLabelClick");
    }
}
