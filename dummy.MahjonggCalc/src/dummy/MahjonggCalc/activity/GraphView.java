package dummy.MahjonggCalc.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphView extends View {
    private static final String TAG = "GraphView";
    private int countDataLines;
    private List<Integer[]> valueList = new ArrayList<Integer[]>();

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getCountDataLines() {
        return countDataLines;
    }

    public void setCountDataLines(int countDataLines) {
        this.countDataLines = countDataLines;
    }

    public void addValues(Integer[] values) {
        if (values.length != countDataLines) {
            throw new RuntimeException("array size does not match countDataLines");
        }
        this.valueList.add(values);
    }

    public void clearValues() {
        valueList.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Integer[] values : this.valueList) {
            for (Integer value : values) {
                if (value != null) {
                    min = Math.min(min, value);
                    max = Math.max(max, value);
                }
            }
        }
        Log.d(TAG, String.format("min/max: %s/%s", min, max));

        int delta = max - min;
        float scaleY = (float)getHeight() / delta;
        float scaleX = (float)getWidth() / (valueList.size() - 1);
        Log.d(TAG, String.format("scale: %s/%s", scaleX, scaleY));

        Paint paint = new Paint();
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

//        int index = 0;
        float lastY[] = new float[countDataLines];
//        for (int x = 0; x < getWidth(); x+=scaleX) {
        for (int index = 0; index < valueList.size(); index++) {
            Integer[] currentValues = valueList.get(index);
            float x = index * scaleX;
            for (int dataLine = 0; dataLine < countDataLines; dataLine++) {
                float y = getHeight() - (currentValues[dataLine] - min) * scaleY;
                if (index > 0) {
                    canvas.drawLine(x - scaleX, lastY[dataLine], x, y, paint);
                }
                lastY[dataLine] = y;
            }
//            index++;
        }


//        Log.d(TAG, String.format("canvas size %s x %s", getWidth(), getHeight()));
    }
}
