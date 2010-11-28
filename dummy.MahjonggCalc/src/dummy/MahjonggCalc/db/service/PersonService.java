package dummy.MahjonggCalc.db.service;

import android.app.Activity;
import android.content.Intent;
import dummy.MahjonggCalc.db.model.Person;

public interface PersonService extends Service<Person> {
	void startPickActivityForResult(int requestCode);
    Person fromActivityResult(Intent data);
}
