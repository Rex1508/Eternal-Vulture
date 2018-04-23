package edu.coe.jlgarcia.eternalvulture;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class InitialDataInfo extends AppCompatActivity implements View.OnClickListener{


    Button btn_autofill;
    Button btn_next;
    EditText collector_name;
    EditText collection_date;
    EditText collection_time;
    EditText collection_location;
    CheckBox YSI556;
    CheckBox YSI55;
    CheckBox AccAP;
    CheckBox HachP;
    CheckBox YSIPro;
    CheckBox HachQ;
    CheckBox Acc61;
    //private Handler refreshHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_data_info);

        idControls();
    }


    private void idControls(){
        btn_autofill = (Button) this.findViewById(R.id.btn_autofill);
        btn_autofill.setOnClickListener(this);
        btn_next = (Button) this.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        collector_name = (EditText) this.findViewById(R.id.collector_name);
        collection_date = (EditText) this.findViewById(R.id.collection_date);
        collection_time = (EditText) this.findViewById(R.id.collection_time);
        collection_location = (EditText) this.findViewById(R.id.collection_location);
        YSI556 = (CheckBox) this.findViewById(R.id.YSI556);
        YSI55 = (CheckBox) this.findViewById(R.id.YSI55);
        AccAP = (CheckBox) this.findViewById(R.id.AccAP);
        HachP = (CheckBox) this.findViewById(R.id.HachP);
        YSIPro = (CheckBox) this.findViewById(R.id.YSIPro);
        HachQ = (CheckBox) this.findViewById(R.id.HachQ);
        Acc61 = (CheckBox) this.findViewById(R.id.Acc61);
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next:

                if (valid()) {
                    saveData();
                    //saveData1();

                    Intent i = new Intent(InitialDataInfo.this, Measurements.class);

                    //i.putExtra("Initial_Info", /*string with csvs to pass on*/);
                    startActivity(i);
                }

                else{error();}
                break;
            case R.id.btn_autofill:
                // Autofill actions //

                getTime();
                //getLocation();

        }

    }


    private boolean valid(){
        boolean valid = true;

        if (collector_name.getText().toString().equals("")){valid = false;}
        if (collection_date.getText().toString().equals("")){valid = false;}
        if (collection_location.getText().toString().equals("")){valid = false;}
        if (collection_time.getText().toString().equals("")){valid = false;}


        return valid;
    }


    private void getTime(){
        Calendar c = Calendar.getInstance();

        // For some god-forsaken reason months start at 0 in here
        String date = (c.get(Calendar.MONTH)+1) + "/"
                + c.get(Calendar.DAY_OF_MONTH)
                + "/" + c.get(Calendar.YEAR);

        String time =c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);

        collection_date.setText(date);
        collection_time.setText(time);
    }

/* We gave up on auto-filling this field
    private void getLocation(){
        collection_location.setText("Coe College");//need to build this up

    }
*/


    private void error(){
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
        txt.setText("One or more required fields were left blank");
        ll_main.addView(txt);

        alertDialog.setTitle("ERROR");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();

    }

    private void saveData(){
        SharedPreferences s;
        s = getSharedPreferences("DataFile",0);
        SharedPreferences.Editor e = s.edit();

        e.putString("collector_name",collector_name.getText().toString());
        e.putString("collection_date",collection_date.getText().toString());
        e.putString("collection_time",collection_time.getText().toString());
        e.putString("collection_location",collection_location.getText().toString());

        e.putBoolean("YSI556",YSI556.isChecked());
        e.putBoolean("YSI55",YSI55.isChecked());
        e.putBoolean("AccAP",AccAP.isChecked());
        e.putBoolean("HachP",HachP.isChecked());
        e.putBoolean("YSIPro",YSIPro.isChecked());
        e.putBoolean("HachQ",HachQ.isChecked());
        e.putBoolean("Acc61",Acc61.isChecked());

        e.apply();

    }




/*
    public void saveData1() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        File file = new File(baseDir+"/"+fileName);

        SharedPreferences s;
        s = getSharedPreferences("DataFile",0);



        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try {
            try {

                fos.write(String.valueOf("hello").getBytes());


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

*/

}
