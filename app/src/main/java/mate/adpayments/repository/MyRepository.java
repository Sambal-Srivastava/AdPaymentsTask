package mate.adpayments.repository;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mate.adpayments.model.DatabaseHelper;

public class MyRepository {

    private SQLiteDatabase database;

    public MyRepository(Application application) {
        DatabaseHelper dbHelper = new DatabaseHelper(application);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getAllData() {
        return database.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    public long insertData(String alias, String name, String privacyUrl) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ALIAS, alias);
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PRIVACY_URL, privacyUrl);
        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public boolean updateData(int id, String alias, String name, String privacyUrl) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ALIAS, alias);
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PRIVACY_URL, privacyUrl);

        String whereClause = DatabaseHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};

        return database.update(DatabaseHelper.TABLE_NAME, values, whereClause, whereArgs) > 0;
    }

    public boolean deleteData(int id) {
        String whereClause = DatabaseHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};

        return database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs) > 0;
    }

    public Cursor getDataById(int itemId) {
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_ALIAS,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_PRIVACY_URL
        };

        String selection = DatabaseHelper.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(itemId)};

        return database.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}

