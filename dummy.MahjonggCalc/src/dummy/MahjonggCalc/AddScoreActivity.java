package dummy.MahjonggCalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddScoreActivity extends Activity {
	private static final String TAG = "AddScoreActivity";
    private RoundScoreCalculator calculator = new RoundScoreCalculator();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score_activity);
    }

    public void onLabelClick(View view) {
    	Log.d(TAG, "onLabelClick");
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
    	RadioButton buttons[] = new RadioButton[] {
    			(RadioButton)findViewById(R.id.RadioButton01),
    			(RadioButton)findViewById(R.id.RadioButton02),
    			(RadioButton)findViewById(R.id.RadioButton03),
    			(RadioButton)findViewById(R.id.RadioButton04),
    			(RadioButton)findViewById(R.id.RadioButton05)
    	};
        Integer winner = 0;
    	for (RadioButton item : buttons) {
    		item.setChecked(item == view);
            if (item == view) {
                if (view == buttons[4]) {
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
//        final List<TextView> labelList = new ArrayList<TextView>();
//        labelList.add((TextView) findViewById(R.id.lblPoints1));
        TextView[][] labelArray = new TextView[][] {
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

        for (int x = 0; x < 4; x++) {
            Integer sum = 0;
            for (int y = 0; y < 4; y++) {
                if (result[x][y] == null) {
                    labelArray[x][y].setText("");
                } else {
                    sum += result[x][y];
                    labelArray[x][y].setText(result[x][y].toString());
                    if (result[x][y] < 0) {
                        labelArray[x][y].setTextColor(getResources().getColor(R.color.red));
                    } else {
                        labelArray[x][y].setTextColor(getResources().getColor(R.color.green));
                    }
                }
            }
            labelArray[x][4].setText(sum.toString());
            if (sum < 0) {
                labelArray[x][4].setTextColor(getResources().getColor(R.color.red));
            } else {
                labelArray[x][4].setTextColor(getResources().getColor(R.color.green));
            }
        }
    }
}
