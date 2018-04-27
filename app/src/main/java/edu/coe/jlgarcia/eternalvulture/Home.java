package edu.coe.jlgarcia.eternalvulture;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Boolean.valueOf;

public class Home extends AppCompatActivity implements View.OnClickListener{



    Button collect_sample;
    Button send_data;
    Button documentation;
    Button erase_data;

    TextView home;

    ReentrantLock lock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 0);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 0);

        idControls();

    }


    public void idControls(){

        collect_sample = (Button) this.findViewById(R.id.btn_Collect_Samples);
        collect_sample.setOnClickListener(this);

        send_data = (Button) this.findViewById(R.id.btn_Send_Data);
        send_data.setOnClickListener(this);

        documentation = (Button) this.findViewById(R.id.btn_Documentation);
        documentation.setOnClickListener(this);


        erase_data = this.findViewById(R.id.btn_Erase_Data);
        erase_data.setOnClickListener(this);

        home = this.findViewById(R.id.txt_home);

        lock = new ReentrantLock();

    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Collect_Samples:

                SharedPreferences s; //opens up the file in the SharedPreferences so we can edit the info in it
                s = getSharedPreferences("DataFile",0);
                SharedPreferences.Editor e = s.edit();

                e.clear();
                e.commit();

                Intent i = new Intent(Home.this, InitialDataInfo.class); // prepare the next activity to be opened
                startActivity(i); //start next activity
                break;
            case R.id.btn_Send_Data:

                try {
                    sendData();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    error(2);
                }

                break;

            case R.id.btn_Documentation:



            case R.id.btn_Erase_Data:
                eraseData();
        }

    }


    public void sendData() throws IOException {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ScrollView scrollview = new ScrollView(this);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        //refreshHandler.post(update);

        TextView header = new TextView(this);
        header.setTextSize(20);
        header.setText("Date                 Location");
        header.setTypeface(Typeface.DEFAULT_BOLD);

        ll_main.addView(header);

        String raw_data = getData();
        String[] data = raw_data.toString().split("\n");
        String split_data[];

        TextView[] info = new TextView[100];

        for (int i=0; i<100; i++){
            info[i] = new TextView(this);
            info[i].setText("");
            info[i].setTextSize(20);
        }
        if (data.length<100) {
            for (int i = 0; i < data.length; i++) {
                split_data = data[i].split(",");
                info[i].setText("-" + split_data[1] + "    " + split_data[3]);
                //Log.e("TESTING",split_data[1] + "\t" + split_data[3]);
            }
            for (int i = 0; i < data.length; i++) {
                ll.addView(info[i]);
            }
        }
        else{
            info[0].setText("ERROR: Cannot display more than 100 samples");
            ll.addView(info[0]);
        }
        scrollview.addView(ll);
        ll_main.addView(scrollview);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set dialog message
            alertDialogBuilder.setCancelable(false).setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });


        TextView space = new TextView(this);
        space.setText(" ");
        ll_main.addView(space);

        TextView warning = new TextView(this);
        warning.setText("WARNING: Data will be erased from this device after being sent");
        ll_main.addView(warning);

        Button send = new Button(this);
        send.setText("SEND");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //lock.lock();
                new SendRequest().execute();
                //lock.unlock();

                //eraseData();
                alertDialog.dismiss();
            }
        });
        ll_main.addView(send);

        alertDialog.setTitle("Review Data to be sent");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();

    }

    private void eraseData(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);


        ll_main.addView(ll);

        //refreshHandler.post(update);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }});

        TextView txt = new TextView(this);
        txt.setText("This operation cannot be undone");
        ll_main.addView(txt);

        Button erase = new Button(this);
        erase.setText("ERASE DATA ON DEVICE");
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "Data.csv";

                File file = new File(baseDir+"/"+fileName);

                file.delete();

                alertDialog.dismiss();
            }
        });
        ll_main.addView(erase);

        TextView space = new TextView(this);
        space.setText(" ");
        ll_main.addView(space);


        Button cancel = new Button(this);
        cancel.setText("ERASE DATA ON DEVICE");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        ll_main.addView(cancel);

        alertDialog.setTitle("Confirm Delete");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();










    }

    public String getData() throws IOException {

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

        return total.toString();
    }


    public int testData(){
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "Data.csv";

        File file = new File(baseDir+"/"+fileName);

        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }




            // CHECK FOR INTERNET CONNECTION
            //if(new TestConnection().execute().get()){return 5;}
            ////// OR ////////
            //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            /////////////////////////


            return 0;


        }
        catch(FileNotFoundException e){
            return 1;
        }
        catch(IOException e){
            return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    public void error(int num){

        //View view = this.getCurrentFocus();

        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);


        //refreshHandler.post(update);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });

        TextView txt = new TextView(this);
        if (num==1) {
            txt.setText("Data file not readable (Data.csv)");
        }
        if (num==2) {
            txt.setText("IO Error (Data.csv)");
        }
        if (num==3) {
            txt.setText("JSON Error");
        }
        if (num==4) {
            txt.setText("????? Error");
        }
        if(num==5){
            txt.setText("No internet connection");
        }

        ll_main.addView(txt);

        TextView space = new TextView(this);
        space.setText(" ");
        ll_main.addView(space);

        ll_main.addView(ll);

        alertDialog.setTitle("ERROR");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();


    }
}