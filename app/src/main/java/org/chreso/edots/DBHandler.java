package org.chreso.edots;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "edots_db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "meddrug";

    // below variable is for our id column.
    private static final String UUID_COL = "uuid";
    private static final String GENERIC_NAME_COL = "generic_name";
    private static final String BRAND_NAME_COL = "brand_name";
    private static final String FORMULATION_COL = "formulation";
    private static final String GENERIC_INGREDIENTS_COL = "generic_ingredients";
    private static final String GENERIC_STRENGTH_COL = "generic_strength";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + UUID_COL + " TEXT PRIMARY KEY, "
                + GENERIC_NAME_COL + " TEXT,"
                + BRAND_NAME_COL + " TEXT,"
                + FORMULATION_COL + " TEXT,"
                + GENERIC_INGREDIENTS_COL + " TEXT,"
                + GENERIC_STRENGTH_COL + "TEXT)";


        sqLiteDatabase.execSQL(query);
    }

    public void addNewMedDrug(String uuid, String genericName, String brandName, String formulation, String genericIngredients, String genericStrenght){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UUID_COL, uuid);
        values.put(GENERIC_NAME_COL, genericName);
        values.put(BRAND_NAME_COL, brandName);
        values.put(FORMULATION_COL, formulation);
        values.put(GENERIC_INGREDIENTS_COL, genericIngredients);
        values.put(GENERIC_STRENGTH_COL, genericStrenght);
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
