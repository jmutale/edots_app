package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.

    private SQLiteDatabase db;
    private static final String DB_NAME = "edots_db";

    // below int is our database version
    private static final int DB_VERSION = 18;

    // below variable is for our table name.
    private static final String MED_DRUG_TABLE_NAME = "meddrug";

    private static final String CLIENT_TABLE_NAME = "client";

    private static final String MED_DRUG_DISPENSATION_TABLE = "med_drug_dispensation";

    private static final String FACILITY_TABLE = "facility";

    private static final String CLIENT_STATUS_TABLE = "client_status";

    private static final String CLIENT_FEEDBACK_TABLE = "client_feedback";

    private static final String EDOT_SURVEY_TABLE = "edot_survey";

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
                +"dispensation_uuid TEXT,"
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

        String client_status_query = "CREATE TABLE "+ CLIENT_STATUS_TABLE +"("
                + "client_status_uuid TEXT PRIMARY KEY, "
                + "reporting_facility TEXT, "
                + "client_uuid TEXT, "
                + "status_date TEXT, "
                + "client_died TEXT, "
                + "client_died_date TEXT, "
                + "cause_of_death TEXT, "
                + "client_refuses_to_continue_treatment TEXT,"
                + "client_is_lost_to_follow_up TEXT,"
                + "client_transferred_out TEXT, "
                + "client_transferred_out_date TEXT, "
                + "facility_transferred_to TEXT)";
        sqLiteDatabase.execSQL(client_status_query);

        String client_feedback_query = "CREATE TABLE "+ CLIENT_FEEDBACK_TABLE + "("
                + "client_feedback_uuid TEXT PRIMARY KEY, "
                + "client_feedback_date TEXT, "
                + "client_uuid TEXT, "
                + "client_adverse_reaction TEXT, "
                + "client_concerns TEXT, "
                + "advice_given_to_client TEXT)";
        sqLiteDatabase.execSQL(client_feedback_query);

        String edot_survey_query = "CREATE TABLE "+ EDOT_SURVEY_TABLE + "("
                + "edot_survey_uuid TEXT PRIMARY KEY, "
                + "edot_survey_date TEXT, "
                + "client_uuid TEXT, "
                + "is_patient_satisfied_with_edot TEXT, "
                + "reasons_satisfied_or_not TEXT, "
                + "would_client_like_to_continue_with_edot TEXT, "
                + "reasons_client_will_continue_with_edot_or_not TEXT)";
        sqLiteDatabase.execSQL(edot_survey_query);
    }

    public void saveDispensationToDatabase(String dispensation_uuid,String med_drug_uuid , String patient_uuid,String dispensationDate, String dose, String items_per_dose, String frequency, String refill_date, String video_path, String location)
    {
        String INSERT_SQL  = "INSERT INTO med_drug_dispensation (dispensation_uuid,med_drug_uuid,patient_uuid,dispensation_date,dose,items_per_dose,frequency,refill_date, video_path, location)" +
                "VALUES ('"+dispensation_uuid+"','"+med_drug_uuid+"','"+patient_uuid+"','"+dispensationDate+"','"+dose+"','"+items_per_dose+"','"+frequency+"','"+refill_date+"','"+video_path+"', '"+location+"')";
        db.execSQL(INSERT_SQL);

    }

    public void saveClientStatusToDatabase(String client_status_uuid, String reporting_facility, String  client_uuid, String status_date, String client_died, String client_died_date, String cause_of_death, String client_refuses_to_continue_treatment, String client_is_lost_to_follow_up, String client_transferred_out, String client_transferred_out_date, String facility_transferred_to){
        String INSERT_SQL = "INSERT INTO client_status(client_status_uuid,reporting_facility,client_uuid,status_date,client_died,client_died_date,cause_of_death,client_refuses_to_continue_treatment,client_is_lost_to_follow_up,client_transferred_out,client_transferred_out_date,facility_transferred_to)"+
                "VALUES ('"+client_status_uuid+"','"+reporting_facility+"','"+client_uuid+"','"+status_date+"','"+client_died+"','"+client_died_date+"','"+cause_of_death+"','"+client_refuses_to_continue_treatment+"','"+client_is_lost_to_follow_up+"','"+client_transferred_out+"','"+client_transferred_out_date+"','"+facility_transferred_to+"')";
        db.execSQL(INSERT_SQL);
    }

    public void saveClientFeedbackToDatabase(String client_feedback_uuid, String client_feedback_date, String client_uuid, String client_adverse_reaction, String client_concerns, String advice_given_to_client){
        String INSERT_SQL = "INSERT INTO client_feedback(client_feedback_uuid, client_feedback_date, client_uuid, client_adverse_reaction,client_concerns,advice_given_to_client)"+
                "VALUES ('"+client_feedback_uuid+"','"+client_feedback_date+"', '"+client_uuid+"', '"+client_adverse_reaction+"','"+client_concerns+"','"+advice_given_to_client+"')";
        db.execSQL(INSERT_SQL);
    }

    public void saveEDOTSurveyToDatabase(String edot_survey_uuid, String edot_survey_date, String client_uuid, String is_patient_satisfied_with_edot, String reasons_satisfied_or_not, String would_client_like_to_continue_with_edot, String reasons_client_will_continue_with_edot_or_not){
        String INSERT_SQL = "INSERT INTO edot_survey(edot_survey_uuid,edot_survey_date,client_uuid,is_patient_satisfied_with_edot,reasons_satisfied_or_not,would_client_like_to_continue_with_edot,reasons_client_will_continue_with_edot_or_not)"+
                "VALUES ('"+edot_survey_uuid+"','"+edot_survey_date+"', '"+client_uuid+"', '"+is_patient_satisfied_with_edot+"', '"+reasons_satisfied_or_not+"', '"+would_client_like_to_continue_with_edot+"','"+reasons_client_will_continue_with_edot_or_not+"')";
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

                String dispDateRaw = c.getString(3);
                String refillDateRaw = c.getString(7);
                dispensationDate = Date.valueOf(dispDateRaw);
                refillDate = Date.valueOf(refillDateRaw);
                ClientDispensation client = new ClientDispensation(c.getString(0), c.getString(1), c.getString(2),dispensationDate, c.getString(4), c.getString(5), c.getString(6), refillDate, c.getString(8));

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
                dispensationDate = Date.valueOf(c.getString(3));
                refillDate = Date.valueOf(c.getString(7));
                ClientDispensation client = new ClientDispensation(c.getString(0),c.getString(1), c.getString(2),dispensationDate, c.getString(4), c.getString(5), c.getString(6), refillDate, c.getString(8));

                clientDispensations.add(client);
            } while(c.moveToNext());
        }
        c.close();
        return clientDispensations;
    }

    public ArrayList<ClientStatus> getListOfClientStatusFromDatabase(){
        ArrayList<ClientStatus> clientStatuses= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM client_status ", null);
        if (c.moveToFirst()) {
            do {

                    Date statusDate = null;
                    Date clientDiedDate = null;
                    Date clientTransOutDate = null;
                    statusDate = (c.getString(3).equals("null")) ? null : Date.valueOf(c.getString(3));
                    clientDiedDate = (c.getString(5).equals("null")) ? null : Date.valueOf(c.getString(5));
                    clientTransOutDate = (c.getString(10).equals("null")) ? null : Date.valueOf(c.getString(10));

                ClientStatus cs = new ClientStatus(c.getString(0),c.getString(1),c.getString(2),statusDate,c.getString(4),clientDiedDate,c.getString(6),c.getString(7),c.getString(9), clientTransOutDate,c.getString(11));
                clientStatuses.add(cs);
            } while(c.moveToNext());
        }
        c.close();
        return clientStatuses;
    }

    public ArrayList<ClientFeedback> getListOfClientFeedbackEntriesFromDatabase(){
        ArrayList<ClientFeedback> clientFeedbackEntries= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM client_feedback ", null);
        if (c.moveToFirst()) {
            do {
                Date clientFeedbackDate = null;
                String tempClientFeedbackDate = c.getString(1);
                clientFeedbackDate = Date.valueOf(c.getString(1));
                ClientFeedback cf = new ClientFeedback(c.getString(0),clientFeedbackDate,c.getString(2),c.getString(3),c.getString(4),c.getString(5));
                clientFeedbackEntries.add(cf);
            } while (c.moveToNext());
        }
        c.close();
        return clientFeedbackEntries;
    }

    public ArrayList<ClientEDOTSurvey> getListOfClientSurveyRecords(){
        ArrayList<ClientEDOTSurvey> clientSurveyRecords= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM edot_survey ", null);
        if (c.moveToFirst()) {
            do {
                Date clientSurveyDate = null;
                clientSurveyDate = Date.valueOf(c.getString(1));
                ClientEDOTSurvey ces = new ClientEDOTSurvey(c.getString(0),clientSurveyDate,c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
                clientSurveyRecords.add(ces);
            }while (c.moveToNext());

            }
        c.close();
        return clientSurveyRecords;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MED_DRUG_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MED_DRUG_DISPENSATION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FACILITY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENT_FEEDBACK_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENT_STATUS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EDOT_SURVEY_TABLE);
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
