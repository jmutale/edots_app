package org.chreso.edots;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.

    private SQLiteDatabase db;
    private static final String DB_NAME = "edots_db";

    // below int is our database version
    private static final int DB_VERSION = 3;

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
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + UUID_COL + " TEXT PRIMARY KEY, "
                + GENERIC_NAME_COL + " TEXT,"
                + BRAND_NAME_COL + " TEXT,"
                + FORMULATION_COL + " TEXT,"
                + GENERIC_INGREDIENTS_COL + " TEXT,"
                + GENERIC_STRENGTH_COL + " TEXT)";


        sqLiteDatabase.execSQL(query);
    }

    public void addNewMedDrug(String uuid, String genericName, String brandName, String formulation, String genericIngredients, String genericStrength){


        String UPSERT_SQL  = "INSERT OR REPLACE INTO meddrug (uuid,generic_name,brand_name,formulation,generic_ingredients,generic_strength)" +
                "VALUES ('"+uuid+"','"+genericName+"','"+brandName+"','"+formulation+"','"+genericIngredients+"','"+genericStrength+"')";
        db.execSQL(UPSERT_SQL);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
