package com.example.assg3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assg3.networkconnection.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    NetworkConnection networkConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkConnection = new NetworkConnection();

        Button loginButton = findViewById(R.id.button_login);
        Button signupButton = findViewById(R.id.button_signup);
        final EditText usernameText = findViewById(R.id.edit_username);
        EditText passwordText = findViewById(R.id.edit_password);
        TextView details = findViewById(R.id.text_details);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSpecificCredentials getSpecificCredentialsTask = new GetSpecificCredentials();
                String uName = usernameText.getText().toString();
                getSpecificCredentialsTask.execute(uName);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }

    private class GetAllCredentials extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return networkConnection.getAllCredentials();
        }

        @Override
        protected void onPostExecute(String credentials) {
            //TextView detailsTextView = findViewById(R.id.text_details);
            //System.out.println(credentials);

            //detailsTextView.setText(credentials);
        }
    }

    private class GetSpecificCredentials extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0].toString();
            return networkConnection.getSpecificCredential(username);
        }

        @Override
        protected void onPostExecute(String credentials) {
            //TextView detailsTextView = findViewById(R.id.text_details);
            System.out.println(credentials);
            //Toast.makeText(getApplicationContext(), credentials, Toast.LENGTH_SHORT).show();
            final EditText usernameText = findViewById(R.id.edit_username);
            EditText passwordText = findViewById(R.id.edit_password);
            //System.out.println(credentials.substring(1, credentials.length() - 1));

            try {
                JSONObject json = new JSONObject(credentials.substring(1, credentials.length() - 1));
                String username = (String)json.get("username");
                String password = (String)json.get("passwordhash");
                md5Hash m1 = new md5Hash();
                String clientPass = passwordText.getText().toString();
                String hashedPass = m1.getMd5(clientPass);
                System.out.println(username);
                System.out.println(usernameText.getText().toString());
                System.out.println(password);
                System.out.println(hashedPass);
                if (username.equals(usernameText.getText().toString()) && password.equals(hashedPass)) {
                    Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    intent.putExtra("User_ID", username);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //detailsTextView.setText(credentials);
        }
    }

    private class md5Hash {
        public String getMd5(String input)
        {
            try {

                // Static getInstance method is called with hashing MD5
                MessageDigest md = MessageDigest.getInstance("MD5");

                // digest() method is called to calculate message digest
                //  of an input digest() return array of byte
                byte[] messageDigest = md.digest(input.getBytes());

                // Convert byte array into signum representation
                BigInteger no = new BigInteger(1, messageDigest);

                // Convert message digest into hex value
                String hashtext = no.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                return hashtext;
            }

            // For specifying wrong message digest algorithms
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
