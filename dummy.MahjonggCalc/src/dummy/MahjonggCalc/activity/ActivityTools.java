package dummy.MahjonggCalc.activity;

import android.widget.TextView;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;
import dummy.MahjonggCalc.R;

public class ActivityTools {
    public static void setLabel(TextView textView, Integer value) {
        textView.setText(value.toString());
        if (value < 0) {
            textView.setTextColor(textView.getResources().getColor(R.color.red));
        } else {
            textView.setTextColor(textView.getResources().getColor(R.color.green));
        }
    }
}
