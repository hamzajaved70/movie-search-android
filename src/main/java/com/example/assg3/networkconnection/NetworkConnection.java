package com.example.assg3.networkconnection;

import com.example.assg3.Credential;
import com.example.assg3.Person;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client=null;
    private String credentials;
    private String credential;
    private String memoirs;
    private String record;
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");
    public NetworkConnection(){
        client = new OkHttpClient();
    }
    private static final String BASE_URL =
            "http://192.168.1.112:15789/Assignment1_Hamza_Javed_30311470/webresources/";

    public String getAllCredentials(){
        final String methodPath = "assignment1.credentials/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            credentials = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return credentials;
    }

    public String getSpecificCredential(String username){
        final String methodPath = "assignment1.credentials/";
        final String stringPath = "findByUsername/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath + stringPath + username + '?');
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            credential = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return credential;
    }

    public static void createCredential(Credential credential){
        //initialise
        URL url = null;

        HttpURLConnection conn = null;
        final String methodPath="/student.course/";
        try {
            Gson gson =new Gson();
            String stringCredentialJson = gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createPerson(Person person){
        //initialise
        URL url = null;

        HttpURLConnection conn = null;
        final String methodPath="/assignment1.memoir/";
        try {
            Gson gson =new Gson();
            String stringPersonJson = gson.toJson(person);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringPersonJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringPersonJson);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public String getHomeScreenMovies(Integer personId){
        final String methodPath = "assignment1.memoir/";
        final String stringPath = "task4f/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath + stringPath + personId);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            memoirs = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return memoirs;
    }

    public String searchMovie(String movieTitle){
        Request.Builder builder = new Request.Builder();
        builder.url("https://api.themoviedb.org/3/search/movie?api_key=8df3ea16fe3e324380633af736b6949f&query="+ movieTitle);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            record = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public String searchMovieById(Integer movieId){
        Request.Builder builder = new Request.Builder();
        builder.url("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=8df3ea16fe3e324380633af736b6949f");
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            record = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public String searchCastById(Integer movieId){
        Request.Builder builder = new Request.Builder();
        builder.url("https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=8df3ea16fe3e324380633af736b6949f");
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            record = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }
}

