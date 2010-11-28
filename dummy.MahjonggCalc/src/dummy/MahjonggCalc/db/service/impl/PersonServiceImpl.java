package dummy.MahjonggCalc.db.service.impl;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import com.google.inject.Inject;
import dummy.MahjonggCalc.db.model.Person;
import dummy.MahjonggCalc.db.service.PersonService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private static final String TAG = "PersonServiceImpl";
    private Activity activity;

    @Inject
    public PersonServiceImpl(Activity activity) {
        this.activity = activity;
    }

    public Person findById(Long id) {
        Uri contactData = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        Cursor c = activity.managedQuery(contactData, null, null, null, null);
        if (c == null) {
            Log.e(TAG, "c is null");
            return null;
        }

        if (!c.moveToFirst()) {
            Log.e(TAG, "person not found");
            return null;
        }

        Person person = new Person();
        person.setId(id);
        person.setName(c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));

        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(activity.getContentResolver(), uri); // <-- always null
        if (photoDataStream != null) {
            Bitmap photo = BitmapFactory.decodeStream(photoDataStream);
            person.setImage(photo);
        }

        return person;
    }

    public void startPickActivityForResult(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    public Person fromActivityResult(Intent data) {
        Uri contactData = data.getData();
        Long id = (Long) ContentUris.parseId(contactData);
        Log.d(TAG, "id: " + id);

        return findById(id);
    }

    public List<Person> list() {
        throw new NotImplementedException();
    }

    public void save(Person obj) {
        throw new NotImplementedException();
    }

    public void update(Person obj) {
        throw new NotImplementedException();
    }

    public void saveOrUpdate(Person obj) {
        throw new NotImplementedException();
    }

    public void delete(Person obj) {
        throw new NotImplementedException();
    }
}
