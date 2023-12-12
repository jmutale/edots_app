package org.chreso.edots;

import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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

    private String id;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView txtForgotPass;
    private Validator validator;
    private ProgressBar progressBarLogin;
    private String first_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkIfLoggedIn();
        progressBarLogin = findViewById(R.id.progressBarLogin);
        validator = new Validator(this);
        validator.setValidationListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextTextPassword);
        id = "";
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
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Confirm exit")
                        .setMessage("You are about to leave the eDOT app to open the password reset page on the server." +
                                "Click Yes to continue.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openResetPasswordWebPageOnServer();
                            }
                        })
                        ;

                builder.create();
                builder.show();

            }
        });

    }

    private void openResetPasswordWebPageOnServer() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utils.getServerUrl(getApplicationContext())+"/accounts/password_reset/"));
        startActivity(browserIntent);
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
        Bundle b = new Bundle();
        b.putString("first_name", first_name);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(b);
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
                            setFirstNameAndUserId();
                            setOfflineLoginDetails(id, username, password);
                            goToMainActivity();
                        }else{
                            Toast.makeText(getContext(),"Something wrong's.",Toast.LENGTH_LONG).show();
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

    private void setOfflineLoginDetails(String id, String username, String password) {
        editor.putString("chw_id", id);
        editor.putString("username",username);
        editor.putString("password", password);
        editor.commit();
    }

    private void setFirstNameAndUserId() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(getContext()).getString("server",null))
                .client(okHttpClient)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<LoggedInUser> call = api.getLoggedInUser("Token "+Utils.getAuthToken(getContext()));

        call.enqueue(new Callback<LoggedInUser>() {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                if (response.isSuccessful()) {
                    LoggedInUser loggedInUser = (LoggedInUser) response.body();
                    if(loggedInUser!=null){
                        id = loggedInUser.getId();
                        first_name = loggedInUser.getFirst_name();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {

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
             new LoginAsyncTask().execute();
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

    private class LoginAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLogin.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            doLogin(username.getText().toString(), password.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBarLogin.setVisibility(View.GONE);

        }
    }
}