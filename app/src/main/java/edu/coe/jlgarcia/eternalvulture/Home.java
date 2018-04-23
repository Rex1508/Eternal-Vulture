package edu.coe.jlgarcia.eternalvulture;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import static java.lang.Boolean.valueOf;

public class Home extends AppCompatActivity implements View.OnClickListener{



    Button collect_sample;
    Button send_data;
    Button erase_data;
    Button review_sample;
    TextView home;



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


        erase_data = this.findViewById(R.id.btn_Erase_Data);
        erase_data.setOnClickListener(this);

        home = this.findViewById(R.id.txt_home);



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
                int result = getData();

                if (result!=0){error(result);}
                else{new SendRequest().execute();}

                break;


            case R.id.btn_Erase_Data:
                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "Data.csv";

                File file = new File(baseDir+"/"+fileName);

                file.delete();

        }

    }



    public int getData(){
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "Data.csv";

        File file = new File(baseDir+"/"+fileName);

        String data;

        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            //home.setText(total.toString());

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