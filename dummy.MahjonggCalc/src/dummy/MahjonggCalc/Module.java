package dummy.MahjonggCalc;

import dummy.MahjonggCalc.db.service.impl.DatabaseHelper;
import dummy.MahjonggCalc.db.service.GameSessionService;
import dummy.MahjonggCalc.db.service.PersonService;
import dummy.MahjonggCalc.db.service.PlayerRoundService;
import dummy.MahjonggCalc.db.service.RoundService;
import dummy.MahjonggCalc.db.service.impl.*;
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
        bind(GameSessionService.class).to(GameSessionServiceImpl.class);
        bind(PlayerRoundService.class).to(PlayerRoundServiceImpl.class);
        bind(RoundService.class).to(RoundServiceImpl.class);

        DatabaseHelper helper = new DatabaseHelper(context);
        bind(DatabaseHelper.class).toInstance(helper);
    }

}
