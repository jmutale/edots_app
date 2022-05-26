package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.

    private SQLiteDatabase db;
    private static final String DB_NAME = "edots_db";

    // below int is our database version
    private static final int DB_VERSION = 15;

    // below variable is for our table name.
    private static final String MED_DRUG_TABLE_NAME = "meddrug";

    private static final String CLIENT_TABLE_NAME = "client";

    private static final String MED_DRUG_DISPENSATION_TABLE = "med_drug_dispensation";

    private static final String FACILITY_TABLE = "facility";

    // below variable is for our id column.
    private static final String UUID_COL = "uuid";
    private static final String GENERIC_NAME_COL = "generic_name";
    private static final String BRAND_NAME_COL = "brand_name";
    private static final String FORMULATION_COL = "formulation";
    private static final String GENERIC_INGREDIENTS_COL = "generic_ingredients";
    private static final String GENERIC_STRENGTH_COL = "generic_strength";
    private DateFormat df;
    private Date dispensationDate;
    private Date refillDate;

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        df = new SimpleDateFormat("dd/MM/yyyy");
        dispensationDate=null;
        refillDate = null;
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
                + "chreso_id TEXT, "
                + "art_number TEXT,"
                + "first_name TEXT, "
                + "last_name TEXT, "
                + "date_of_birth TEXT, "
                + "sex TEXT, "
                + "mobile_phone_number TEXT)";
        sqLiteDatabase.execSQL(client_table_query);

        String dispensation_table_query = "CREATE TABLE "+MED_DRUG_DISPENSATION_TABLE + "("
                + "med_drug_uuid TEXT, "
                + "patient_uuid TEXT, "
                + "dispensation_date TEXT, "
                + "dose TEXT, "
                + "items_per_dose TEXT, "
                + "frequency TEXT, "
                + "refill_date TEXT, "
                + "video_path TEXT,"
                +  "location TEXT)";
        sqLiteDatabase.execSQL(dispensation_table_query);

        String facility_table_query = "CREATE TABLE "+ FACILITY_TABLE + "("
                + "facility_uuid TEXT PRIMARY KEY, "
                + "name TEXT, "
                + "code TEXT, "
                + "supported TEXT, "
                + "type TEXT, "
                + "point TEXT, "
                + "parent TEXT)";
        sqLiteDatabase.execSQL(facility_table_query);
    }

    public void saveDispensationToDatabase(String med_drug_uuid , String patient_uuid,String dispensationDate, String dose, String items_per_dose, String frequency, String refill_date, String video_path, String location)
    {
        String INSERT_SQL  = "INSERT INTO med_drug_dispensation (med_drug_uuid,patient_uuid,dispensation_date,dose,items_per_dose,frequency,refill_date, video_path, location)" +
                "VALUES ('"+med_drug_uuid+"','"+patient_uuid+"','"+dispensationDate+"','"+dose+"','"+items_per_dose+"','"+frequency+"','"+refill_date+"','"+video_path+"', '"+location+"')";
        db.execSQL(INSERT_SQL);

    }

    public void addNewMedDrug(String uuid, String genericName, String brandName, String formulation, String genericIngredients, String genericStrength){


        String UPSERT_SQL  = "INSERT OR REPLACE INTO meddrug (uuid,generic_name,brand_name,formulation,generic_ingredients,generic_strength)" +
                "VALUES ('"+uuid+"','"+genericName+"','"+brandName+"','"+formulation+"','"+genericIngredients+"','"+genericStrength+"')";
        db.execSQL(UPSERT_SQL);
        //db.close();
    }

    public void addNewClient(String uuid, String nrcNumber, String chresoId, String artNumber, String firstName, String lastName, String dateOfBirth, String sex, String mobilePhoneNumber){


            String UPSERT_SQL = "INSERT OR REPLACE INTO client (uuid, nrc_number, chreso_id, art_number,first_name,last_name,date_of_birth,sex,mobile_phone_number)" +
                    "VALUES ('"+uuid+"','"+nrcNumber+"', '"+chresoId+"', '"+artNumber+"','"+firstName+"','"+lastName+"','"+dateOfBirth+"','"+sex+"','"+mobilePhoneNumber+"')";
            db.execSQL(UPSERT_SQL);

    }

    public void addNewLocation(String uuid, String name, String code, String supported, String type, String point, String parent)
    {
        String UPSERT_SQL = "INSERT OR REPLACE INTO facility(facility_uuid, name,code, supported, type, point, parent)"+
                "VALUES ('"+uuid+"', '"+name+"', '"+code+"', '"+supported+"','"+type+"', '"+point+"','"+parent+"')";
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
                Client client = new Client(c.getString(0), c.getString(1),c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6),c.getString(7), c.getString(8));

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

                String dispDateRaw = c.getString(2);
                String refillDateRaw = c.getString(6);
                dispensationDate = Date.valueOf(dispDateRaw);
                refillDate = Date.valueOf(refillDateRaw);
                ClientDispensation client = new ClientDispensation(c.getString(0), c.getString(1),dispensationDate, c.getString(3), c.getString(4), c.getString(5), refillDate, c.getString(7));

                clientDispensations.add(client);
            } while(c.moveToNext());
        }
        c.close();
        return clientDispensations;
    }

    public ArrayList<ClientDispensation> getListOfClientDispensationsFromDatabase()
    {
        ArrayList<ClientDispensation> clientDispensations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM med_drug_dispensation ", null);
        if (c.moveToFirst()){
            do {
                Date dispensationDate = null;
                Date refillDate = null;
                dispensationDate = Date.valueOf(c.getString(2));
                refillDate = Date.valueOf(c.getString(6));
                ClientDispensation client = new ClientDispensation(c.getString(0), c.getString(1),dispensationDate, c.getString(3), c.getString(4), c.getString(5), refillDate, c.getString(7));

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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FACILITY_TABLE);
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

    public String getNrcFromClientUuid(String client_uuid)
    {
        String nrc = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT nrc_number FROM client WHERE uuid ='"+client_uuid+"'", null);
        if (c.moveToFirst()){
            do {
                nrc = c.getString(0);
            } while(c.moveToNext());
        }
        c.close();
        return nrc;
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

    public Map<String, String> getListOfHealthFacilitiesFromDatabase() {
        Map<String, String> map =
                new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT code,name FROM facility WHERE type = 'health_facility'", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String code = c.getString(0);
                String name = c.getString(1);

                map.put(code,name);
            } while(c.moveToNext());
        }
        c.close();
        //db.close();
        return map;
    }
}
