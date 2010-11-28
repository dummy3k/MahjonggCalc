package dummy.MahjonggCalc;

import java.util.List;

import android.util.Log;
import roboguice.application.GuiceApplication;

public class MyApplication extends GuiceApplication {
    private static final String TAG = "MyApplication";

	protected void addApplicationModules(List<com.google.inject.Module> modules) {
        Log.d(TAG, "addApplicationModules()");
		modules.add(new Module(this));
	}
}
