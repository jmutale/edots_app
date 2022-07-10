package org.chreso.edots;

import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends EdotActivity implements Validator.ValidationListener {

    private Button btnLogin;
    @NotEmpty
    private EditText username;
    @NotEmpty
    private EditText password;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView txtForgotPass;
    private Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkIfLoggedIn();
        validator = new Validator(this);
        validator.setValidationListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextTextPassword);
        txtForgotPass = findViewById(R.id.forgetPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement login loic
                validator.validate();
            }
        });
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open reset password web page
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utils.getServerUrl(getApplicationContext())+"/accounts/password_reset/"));
                startActivity(browserIntent);
            }
        });

    }

    private void checkIfLoggedIn(){
        String token = PreferenceManager
                .getDefaultSharedPreferences(this).getString("token",null);
        if(token!=null){
            goToMainActivity();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkIfLoggedIn();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void doLogin(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(this).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call call = api.login(new LoginBody(username,password));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    AuthToken loginResponse = (AuthToken) response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getToken() != null) {
                            setAuthToken(loginResponse.getToken());
                            goToMainActivity();
                        }else{
                            Toast.makeText(getContext(),"Something wrong with the Body",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(),"The username or password is incorrect",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(getContext(),"The username or password is incorrect",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private Context getContext(){
        return getApplicationContext();
    }

    private void setAuthToken(String token) {
        editor.putString("token",token);
        editor.commit();
    }

    @Override
    public void onValidationSucceeded() {
        if(PreferenceManager
                .getDefaultSharedPreferences(this).getString("server",null)!=null) {
            doLogin(username.getText().toString(), password.getText().toString());
        }else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Server Error")
                    .setMessage("Please assign a server URL value before you log in.")
                    .setCancelable(true)

                    ;
            builder.create();
            builder.show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
            }
        }
    }
}