package edu.coe.jlgarcia.eternalvulture;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;



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

//import androidlabs.gsheets2.R;




/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Observations extends AppCompatActivity implements View.OnClickListener{

    Button finish;

    EditText edt_comments;

    CheckBox highwater;
    CheckBox lowwater;
    CheckBox debris;
    CheckBox sample1;
    CheckBox sample2;
    CheckBox sample3;

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = "Data.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observations);

        idControls();
    }

    private void idControls(){
        finish = this.findViewById(R.id.btn_Finish);
        finish.setOnClickListener(this);

        edt_comments = this.findViewById(R.id.edt_comments);

        highwater = this.findViewById(R.id.CB_High_Water);
        lowwater = this.findViewById(R.id.CB_Low_Water);
        debris = this.findViewById(R.id.CB_Debris);
        sample1 = this.findViewById(R.id.CB_Sample1);
        sample2 = this.findViewById(R.id.CB_Sample2);
        sample3 = this.findViewById(R.id.CB_Sample3);

    }

    public void onClick(View v) {
        // improves = add things for the other buttons!
        switch (v.getId()) {
            case R.id.btn_Finish: //If e click the finish button, it will complete and go to the home page

                if (valid())
                {
                saveData(); // save all the data that has been collected

                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                }
                else{error();}
                break;
        }
    }

    public void saveData() {

        File file = new File(baseDir+"/"+fileName);

        SharedPreferences s;
        s = getSharedPreferences("DataFile",0);



        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file,true);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try {
            try {

                fos.write(s.getString("collector_name", "NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("collection_date", "NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("collection_time", "NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("collection_location", "NOT_FOUND").getBytes());
                fos.write(",".getBytes());

                if (s.getBoolean("YSI556",false)){fos.write("YSI556 ".getBytes());}
                if (s.getBoolean("YSI55",false)){fos.write("YSI55 ".getBytes());}
                if (s.getBoolean("AccAP",false)){fos.write("AccAP ".getBytes());}
                if (s.getBoolean("HachP",false)){fos.write("HachP ".getBytes());}
                if (s.getBoolean("YSIPro",false)){fos.write("YSIPro ".getBytes());}
                if (s.getBoolean("HachQ",false)){fos.write("HachQ ".getBytes());}
                if (s.getBoolean("Acc61",false)){fos.write("Acc61 ".getBytes());}
                fos.write(",".getBytes());

                if (s.getBoolean("stream",false)){fos.write("stream ".getBytes());}
                if (s.getBoolean("bucket",false)){fos.write("bucket".getBytes());}
                fos.write(",".getBytes());

                fos.write(s.getString("oxygen","NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("temp","NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("ph","NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("conductance","NOT_FOUND").getBytes());
                fos.write(",".getBytes());
                fos.write(s.getString("turbidity","NOT_FOUND").getBytes());
                fos.write(",".getBytes());


                if (highwater.isChecked()){fos.write("High water ".getBytes());}
                if (lowwater.isChecked()){fos.write("Low water ".getBytes());}
                if (debris.isChecked()){fos.write("Debris ".getBytes());}
                fos.write(",".getBytes());


                /*
                fos.write(String.valueOf(sample1.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(sample2.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(sample3.isChecked()).getBytes());
                fos.write(",".getBytes());
                */


                fos.write((edt_comments.getText().toString()+" ").getBytes());

                fos.write("\n".getBytes());
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public boolean valid()
    {
        boolean valid = true;
        if(highwater.isChecked() && lowwater.isChecked()) valid = false;
        if(sample1.isChecked()==false || sample2.isChecked()==false || sample3.isChecked()==false){
            valid = false;
        }
        return valid;
    }

    private void error() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll_main.addView(ll);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });

        if (highwater.isChecked() && lowwater.isChecked()) //message to give for high and low water
        {
            TextView txt1 = new TextView(this);
            txt1.setText("You cannot select both High water and Low Water");
            txt1.setTextSize(20);
            ll.addView(txt1);
        }
        if (sample1.isChecked()==false || sample2.isChecked()==false || sample3.isChecked()==false)
        {
            TextView txt2 = new TextView(this);
            txt2.setText("Make sure you label all samples");
            txt2.setTextSize(20);
            ll.addView(txt2);
        }

        alertDialog.setView(ll_main);
        alertDialog.show();

    }
    }






















