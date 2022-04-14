package org.chreso.edots;


import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends EdotActivity {

    private static final String TAG = "Main";
    private Button btnPostData;
    private ExecutorService myExecutor;
    private EditText textBox;
    private TextView text;
    private ListView list;

    ArrayAdapter adapter;
    ClientAdapter clientAdapter;

    private DBHandler dbHandler;



    //String patient[] = {"John","Terry","Henry","Chisanga","Mildred","Thomas","Mutale","Steven","Trevor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);

        textBox=(EditText)findViewById(R.id.textBox);
        text=(TextView)findViewById(R.id.text);
        list=(ListView)findViewById(R.id.list);

        setPrefs(PreferenceManager
                .getDefaultSharedPreferences(this));
        //String patient[] = dbHandler.getListOfClientFromDatabase().toArray(new String[0]);
        //adapter=new ArrayAdapter(this,R.layout.list_item,R.id.text,patient);
        ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();
        clientAdapter = new ClientAdapter(this,arrayOfClients);
        list.setAdapter(clientAdapter);


        textBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                clientAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView fName = (TextView)view.findViewById(R.id.fName);
                TextView lName = (TextView)view.findViewById(R.id.lName);
                TextView dob = (TextView)view.findViewById(R.id.sDob);
                TextView sex = (TextView)view.findViewById(R.id.sex);
                TextView phone = (TextView)view.findViewById(R.id.mPhone);
                openClientRecord(fName.getText().toString(), lName.getText().toString(), dob.getText().toString(),sex.getText().toString(), phone.getText().toString());
            }
        }
    );
}





    private void openClientRecord(String first_name, String l_name, String dob, String sex, String mobilePhoneNumber) {
        Bundle b = new Bundle();
        b.putString("fName", first_name);
        b.putString("lName", l_name);
        b.putString("dob",dob);
        b.putString("sex",sex);
        b.putString("mobile", mobilePhoneNumber);
        Intent intent = new Intent(this, ClientMain.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void startDataSync() {

        //Handler myHandler = new Handler(Looper.getMainLooper());

        //myExecutor.execute(new NetworkTask());

    }




}