package edu.coe.jlgarcia.eternalvulture;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SendRequest extends AsyncTask<String, Void, String> {


    protected void onPreExecute(){

    }

    protected String doInBackground(String... arg0) {

        String result = "Something went wrong in SendRequest.java, doInBackground()";

        try {
            //URL url = new URL("https://script.google.com/macros/s/AKfycby9uEj0M4N5yz7EDwNxn4NWkslqXAJfqTOD66IGlTvqlgi8ylw/exec\n");
            URL url = new URL("https://script.google.com/macros/s/AKfycbwrFYSbWdoH27SsbOp-DCqTTnpT3lXUUEL0_zwj/exec\n");
            JSONObject postDataParams = new JSONObject();


            /////////////////////////////////////////////////////////
            // ID OF GOOGLE SHEET "Water Quality Data" owned by eternalvulture@gmail.com
            String id = "1gAE8K4jsdEYb2s2ssmd2SQUE0EwcV7wYelkf4dIHJGU";
            /////////////////////////////////////////////////////////


            String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "Data.csv";

            File file = new File(baseDir + "/" + fileName);
            InputStream inputStream = new FileInputStream(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line_in;

            while ((line_in = r.readLine()) != null) {
                total.append(line_in);
                total.append('\n');
            }

            //Log.e("DATA FROM FILE", total.toString());

            String[] data;
            String[] raw_data = total.toString().split("\n");
            Log.e("LENGTH OF RAW_DATA",String.valueOf(raw_data.length));
            for (int i = 0; i < raw_data.length; i++) {
                Log.e("DATA FROM FILE", raw_data[i]);

                data = raw_data[i].split(",");

                postDataParams.put("name", data[0]);
                postDataParams.put("date", data[1]);
                postDataParams.put("time", data[2]);
                postDataParams.put("location", data[3]);
                postDataParams.put("equipment", data[4]);
                postDataParams.put("method", data[5]);
                postDataParams.put("oxygen", data[6]);
                postDataParams.put("temp", data[7]);
                postDataParams.put("ph", data[8]);
                postDataParams.put("conductance", data[9]);
                postDataParams.put("turbidity", data[10]);
                postDataParams.put("fieldnitrate", data[11]);
                postDataParams.put("observations", data[12]);
                postDataParams.put("comments", data[13]);
                postDataParams.put("id", id);


                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
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
                    result = sb.toString();

                } else {
                    result = new String("false : " + responseCode);
                }
            }
        }
        catch(Exception e){
            result = new String("Exception: " + e.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result,
          //      Toast.LENGTH_LONG).show();

    }

//}

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
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
}