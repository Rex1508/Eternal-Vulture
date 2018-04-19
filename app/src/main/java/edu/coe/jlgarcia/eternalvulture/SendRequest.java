package edu.coe.jlgarcia.eternalvulture;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ian on 4/11/2018.
 */

public class SendRequest extends AsyncTask<String, Void, String> {

    /////////////////////////////////////////////////////////
    ///////////// ERASE THIS /////////////////////////////
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }



/*
    protected void onPreExecute() {
    }

    protected String doInBackground(String... arg0) {

        try {

            String name;
            String date;
            String time;
            String location;
            String equipment;
            String method;
            String oxygen;
            String temp;
            String ph;
            String conductance;
            String turbidity;
            String observations;
            String comments;

            SharedPreferences s;
            s = getSharedPreferences("DataFile", 0);

            name = s.getString("name", null);
            date = s.getString("date", null);
            time = s.getString("time", null);
            location = s.getString("location", null);
            equipment = s.getString("equipment", null);
            method = s.getString("method", null);
            oxygen = s.getString("oxygen", null);
            temp = s.getString("temp", null);
            ph = s.getString("ph", null);
            conductance = s.getString("conductance", null);
            turbidity = s.getString("turbidity", null);

            observations = "";
            if (highwater.isChecked()) {
                observations += "High Water, ";
            }
            if (debris.isChecked()) {
                observations += "Debris, ";
            }
            if (lowwater.isChecked()) {
                observations += "Low Water";
            }

            comments = edt_comments.getText().toString();


            URL url = new URL("https://script.google.com/macros/s/AKfycby9uEj0M4N5yz7EDwNxn4NWkslqXAJfqTOD66IGlTvqlgi8ylw/exec");
            // https://script.google.com/macros/s/AKfycbyuAu6jWNYMiWt9X5yp63-hypxQPlg5JS8NimN6GEGmdKZcIFh0/exec
            JSONObject postDataParams = new JSONObject();

            //int i;
            //for(i=1;i<=70;i++)


            //    String usn = Integer.toString(i);

            /////////////////// THIS IS THE ID FOR THE GOOGLE SHEETS FILE "Water Quality Data" //////////////////
            String id = "1gAE8K4jsdEYb2s2ssmd2SQUE0EwcV7wYelkf4dIHJGU";
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            postDataParams.put("name", name);
            postDataParams.put("date", date);
            postDataParams.put("time", time);
            postDataParams.put("location", location);
            postDataParams.put("equipment", equipment);
            postDataParams.put("method", method);
            postDataParams.put("oxygen", oxygen);
            postDataParams.put("temp", temp);
            postDataParams.put("ph", ph);
            postDataParams.put("conductance", conductance);
            postDataParams.put("turbidity", turbidity);
            postDataParams.put("observations", observations);
            postDataParams.put("comments", comments);

            postDataParams.put("id", id);


            Log.e("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 // milliseconds //);
            conn.setConnectTimeout(15000 // milliseconds //);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(this, result,
                Toast.LENGTH_LONG).show();

    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    */
}

