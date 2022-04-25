package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.

    private SQLiteDatabase db;
    private static final String DB_NAME = "edots_db";

    // below int is our database version
    private static final int DB_VERSION = 7;

    // below variable is for our table name.
    private static final String MED_DRUG_TABLE_NAME = "meddrug";

    private static final String CLIENT_TABLE_NAME = "client";

    private static final String MED_DRUG_DISPENSATION_TABLE = "med_drug_dispensation";

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
        String query = "CREATE TABLE " + MED_DRUG_TABLE_NAME + " ("
                + UUID_COL + " TEXT PRIMARY KEY, "
                + GENERIC_NAME_COL + " TEXT,"
                + BRAND_NAME_COL + " TEXT,"
                + FORMULATION_COL + " TEXT,"
                + GENERIC_INGREDIENTS_COL + " TEXT,"
                + GENERIC_STRENGTH_COL + " TEXT)";


        sqLiteDatabase.execSQL(query);

        String client_table_query = "CREATE TABLE "+ CLIENT_TABLE_NAME + " ("
                + "uuid TEXT PRIMARY KEY, "
                + "nrc_number TEXT, "
                + "art_number TEXT,"
                + "first_name TEXT, "
                + "last_name TEXT, "
                + "date_of_birth TEXT, "
                + "sex TEXT, "
                + "mobile_phone_number TEXT)";
        sqLiteDatabase.execSQL(client_table_query);

        String dispensation_table_query = "CREATE TABLE "+MED_DRUG_DISPENSATION_TABLE + "("
                + "meddrug_uuid TEXT, "
                + "patient_uuid TEXT, "
                + "dispensation_date TEXT, "
                + "dose TEXT, "
                + "items_per_dose TEXT, "
                + "frequency TEXT, "
                + "refill_date TEXT, "
                + "video_path TEXT)";
        sqLiteDatabase.execSQL(dispensation_table_query);
    }

    public void saveDispensationToDatabase(String meddrug_uuid , String patient_uuid,String dispensationDate, String dose, String items_per_dose, String frequency, String refill_date, String video_path)
    {
        String UPSERT_SQL  = "INSERT INTO med_drug_dispensation (meddrug_uuid,patient_uuid,dispensation_date,dose,items_per_dose,frequency,refill_date, video_path)" +
                "VALUES ('"+meddrug_uuid+"','"+patient_uuid+"','"+dispensationDate+"','"+dose+"','"+items_per_dose+"','"+frequency+"','"+refill_date+"','"+video_path+"')";
        db.execSQL(UPSERT_SQL);

    }

    public void addNewMedDrug(String uuid, String genericName, String brandName, String formulation, String genericIngredients, String genericStrength){


        String UPSERT_SQL  = "INSERT OR REPLACE INTO meddrug (uuid,generic_name,brand_name,formulation,generic_ingredients,generic_strength)" +
                "VALUES ('"+uuid+"','"+genericName+"','"+brandName+"','"+formulation+"','"+genericIngredients+"','"+genericStrength+"')";
        db.execSQL(UPSERT_SQL);
        //db.close();
    }

    public void addNewClient(String uuid, String firstName, String lastName, String dateOfBirth, String sex, String mobilePhoneNumber){
        String UPSERT_SQL = "INSERT OR REPLACE INTO client (uuid,first_name,last_name,date_of_birth,sex,mobile_phone_number)" +
                "VALUES ('"+uuid+"','"+firstName+"','"+lastName+"','"+dateOfBirth+"','"+sex+"','"+mobilePhoneNumber+"')";
        db.execSQL(UPSERT_SQL);
    }

    public Map<String,String> loadDrugsIntoSpinnerFromDatabase() {
        Map<String, String> map =
                new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT uuid,generic_name FROM meddrug ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String uuid = c.getString(0);
                String generic_name = c.getString(1);

                map.put(uuid,generic_name);
            } while(c.moveToNext());
        }
        c.close();
        //db.close();
        return map;
    }

    public ArrayList<Client> getLisOfClientDetailsFromDatabase(){
        ArrayList<Client> clients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM client ", null);
        if (c.moveToFirst()){
            do {
                Client client = new Client(c.getString(0), c.getString(1),c.getString(2), c.getString(3), c.getString(4), c.getString(5));

                clients.add(client);
            } while(c.moveToNext());
        }
        c.close();
        return clients;
    }

    public ArrayList<ClientDispensation> getListOfClientDispensationsFromDatabase(String patientGuid)
    {
        ArrayList<ClientDispensation> clientDispensations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM med_drug_dispensation WHERE patient_uuid ='"+patientGuid+"'", null);
        if (c.moveToFirst()){
            do {
                ClientDispensation client = new ClientDispensation(c.getString(0), c.getString(1),c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));

                clientDispensations.add(client);
            } while(c.moveToNext());
        }
        c.close();
        return clientDispensations;
    }

    public List<String> getListOfClientFromDatabase(){
        List<String> clients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT first_name FROM client ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String column1 = c.getString(0);


                // Do something Here with values
                clients.add(column1);
            } while(c.moveToNext());
        }
        c.close();
        return clients;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MED_DRUG_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MED_DRUG_DISPENSATION_TABLE);
        onCreate(sqLiteDatabase);
    }


    public String getDrugNameFromDatabase(String med_drug_uuid) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT generic_name FROM meddrug WHERE uuid ='"+med_drug_uuid+"'", null);
        if (c.moveToFirst()){
            do {
                name = c.getString(0);
            } while(c.moveToNext());
        }
        c.close();
        return name;
    }

    public String getNumberOfClientsDueForRefillToday(){
        String clientDue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) FROM med_drug_dispensation WHERE refill_date = DATE('now')", null);
        if (c.moveToFirst()){
            do {
                clientDue = c.getString(0);
            } while(c.moveToNext());
        }
        c.close();
        return clientDue;
    }

    public String getNumberOfClientsWithRefillToday(){
        String clientDue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) FROM med_drug_dispensation WHERE dispensation_date = DATE('now')", null);
        if (c.moveToFirst()){
            do {
                clientDue = c.getString(0);
            } while(c.moveToNext());
        }
        c.close();
        return clientDue;
    }

    public boolean doesClientExist(Editable text) {
        return false;
    }
}
