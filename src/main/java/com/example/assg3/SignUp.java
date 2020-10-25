package com.example.assg3;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assg3.Credential;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assg3.networkconnection.NetworkConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignUp extends AppCompatActivity {
    NetworkConnection networkConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        networkConnection = new NetworkConnection();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button signupButton = findViewById(R.id.button_signup);
        EditText firstName = findViewById(R.id.edit_fname);
        EditText lastName = findViewById(R.id.edit_lname);
        Spinner gender = findViewById(R.id.spinner_gender);
        DatePicker dateOfBirth = findViewById(R.id.datePicker1);
        EditText address = findViewById(R.id.edit_address);
        EditText state = findViewById(R.id.edit_state);
        EditText postcode = findViewById(R.id.edit_postcode);
        EditText email = findViewById(R.id.edit_username);
        EditText password = findViewById(R.id.edit_password);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCredential postCredential = new postCredential();
                /*String strAdd = add_editText.getText().toString();
                if (!strAdd.isEmpty()) {
                    addAsyncTask.execute(strAdd);
                }*/
            }
        });

    }

    private class postCredential extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String[] details = params[0].split(" ");
            if (details.length == 4) {
                Credential credential = null;
                try {
                    credential = new Credential(Integer.valueOf(details[0]), details[1], details[2], new SimpleDateFormat("dd/MM/yyyy").parse(details[3]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                networkConnection.createCredential(credential);
            }
            return "Credential was added";
        }

        @Override
        protected void onPostExecute(String response) {
            //TextView detailsTextView = findViewById(R.id.text_details);
            //System.out.println(credentials);
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            //detailsTextView.setText(credentials);
        }
    }

    private class postPerson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String[] details = params[0].split(" ");
            if (details.length == 4) {
                Person person = null;
                try {
                    person = new Person(Integer.valueOf(details[0]), details[1], details[2], details[3],
                            new SimpleDateFormat("dd/MM/yyyy").parse(details[4]), details[5], details[6], Integer.valueOf(details[7]), Integer.valueOf(details[8]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                networkConnection.createPerson(person);
            }
            return "Person was added";
        }

        @Override
        protected void onPostExecute(String response) {
            //TextView detailsTextView = findViewById(R.id.text_details);
            //System.out.println(credentials);
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            //detailsTextView.setText(credentials);
        }
    }
}
