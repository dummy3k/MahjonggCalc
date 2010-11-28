package dummy.MahjonggCalc;

import dummy.MahjonggCalc.db.service.PersonService;
import dummy.MahjonggCalc.db.service.impl.PersonServiceImpl;
import roboguice.config.AbstractAndroidModule;
import android.content.Context;

public class Module extends AbstractAndroidModule {
	private Context context;
	
	public Module(Context context) {
		this.context = context;
	}	
	
    @Override
    protected void configure() {
        bind(PersonService.class).to(PersonServiceImpl.class);

        DatabaseHelper helper = new DatabaseHelper(context);
        bind(DatabaseHelper.class).toInstance(helper);
    }

}
