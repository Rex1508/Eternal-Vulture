package edu.coe.jlgarcia.eternalvulture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Measurements extends AppCompatActivity implements View.OnClickListener {


    Button btn_next;
    CheckBox stream;
    CheckBox bucket;
    EditText oxygen;
    EditText temp;
    EditText ph;
    EditText conductance;
    EditText turbidity;

    // Primes used to signal that certain errors occurred without
    // having to pass a bool array
    int OXYGEN_OOR = 2;
    int OXYGEN_BLANK = 3;
    int TEMP_OOR = 5;
    int TEMP_BLANK = 7;
    int PH_OOR = 11;
    int PH_BLANK = 13;
    int CONDUCTANCE_OOR = 17;
    int CONDUCTANCE_BLANK = 19;
    int TURBIDITY_OOR = 23;
    int TURBIDITY_BLANK = 29;


    double oxygen_min = 6;
    double oxygen_max = 10;
    double temp_min = 8;
    double temp_max = 25;
    double ph_min = 6.5;
    double ph_max = 9;
    double conductance_min = 400;
    double conductance_max = 800;
    double turbidity_min = 0;
    double turbidity_max = 400;

    boolean warned = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurments);


        idControls();
    }


    private void idControls() {
        btn_next = this.findViewById(R.id.btn_next);
        stream = this.findViewById(R.id.stream);
        bucket = this.findViewById(R.id.bucket);
        oxygen = this.findViewById(R.id.oxygen);
        temp = this.findViewById(R.id.temp);
        ph = this.findViewById(R.id.ph);
        conductance = this.findViewById(R.id.conductance);
        turbidity = this.findViewById(R.id.turbidity);

        btn_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                try {
                    double ph_int = Double.valueOf(ph.getText().toString());
                    if (ph_int < 0 || ph_int > 14) {
                        badError();
                        break;
                    }
                }
                catch(Exception e){e.printStackTrace();}
                if (valid() == 1 || warned) {
                    saveData();
                    Intent i = new Intent(Measurements.this, Observations.class);
                    startActivity(i);
                } else {
                    error(valid());
                }
                break;
        }

    }


    private int valid() {
        int valid = 1;

        if (oxygen.getText().toString().equals("")) {
            valid *= OXYGEN_BLANK;
        }
        if (temp.getText().toString().equals("")) {
            valid *= TEMP_BLANK;
        }
        if (ph.getText().toString().equals("")) {
            valid *= PH_BLANK;
        }
        if (conductance.getText().toString().equals("")) {
            valid *= CONDUCTANCE_BLANK;
        }
        if (turbidity.getText().toString().equals("")) {
            valid *= TURBIDITY_BLANK;
        }

        if (OOR(oxygen, oxygen_min, oxygen_max)) {
            valid *= OXYGEN_OOR;
        }
        if (OOR(temp, temp_min, temp_max)) {
            valid *= TEMP_OOR;
        }
        if (OOR(ph, ph_min, ph_max)) {
            valid *= PH_OOR;
        }
        if (OOR(conductance, conductance_min, conductance_max)) {
            valid *= CONDUCTANCE_OOR;
        }
        if (OOR(turbidity, turbidity_min, turbidity_max)) {
            valid *= TURBIDITY_OOR;
        }

        return valid;
    }

    //Returns TRUE if value exists and is out of range
    private boolean OOR(EditText edt, double min, double max) {
        if (!edt.getText().toString().equals("")) {
            return (Double.parseDouble(edt.getText().toString()) < min ||
                    Double.parseDouble(edt.getText().toString()) > max);
        } else {
            return false;
        }
    }


    private void error(int num) {

        View view = this.getCurrentFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        boolean cont = true;


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);
        ll_main.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

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
        txt.setText("Make sure the inputted data is correct");
        ll_main.addView(txt);

        TextView space = new TextView(this);
        space.setText(" ");
        ll_main.addView(space);

        if (num % OXYGEN_BLANK == 0) {
            TextView txt1 = new TextView(this);
            txt1.setText("-Oxygen field left blank");
            txt1.setTextSize(20);
            ll.addView(txt1);
            cont = false;
        }
        if (num % TEMP_BLANK == 0) {
            TextView txt2 = new TextView(this);
            txt2.setText("-Temp field left blank");
            txt2.setTextSize(20);
            ll.addView(txt2);
            cont = false;
        }
        if (num % PH_BLANK == 0) {
            TextView txt3 = new TextView(this);
            txt3.setText("-pH field left blank");
            txt3.setTextSize(20);
            ll.addView(txt3);
            cont = false;
        }
        if (num % CONDUCTANCE_BLANK == 0) {
            TextView txt4 = new TextView(this);
            txt4.setText("-Conductance field left blank");
            txt4.setTextSize(20);
            ll.addView(txt4);
            cont = false;
        }
        if (num % TURBIDITY_BLANK == 0) {
            TextView txt5 = new TextView(this);
            txt5.setText("-Turbidity field left blank");
            txt5.setTextSize(20);
            ll.addView(txt5);
            cont = false;
        }



        if (cont) {

            warned = true;
            if (num % OXYGEN_OOR == 0) {
                TextView txt6 = new TextView(this);
                txt6.setText("-Oxygen value out of normal range");
                txt6.setTextSize(20);
                ll.addView(txt6);
                oxygen.setTextColor(Color.RED);
            }
            if (num % TEMP_OOR == 0) {
                TextView txt7 = new TextView(this);
                txt7.setText("-Temp value out of normal range");
                txt7.setTextSize(20);
                ll.addView(txt7);
                temp.setTextColor(Color.RED);
            }
            if (num % PH_OOR == 0) {
                TextView txt8 = new TextView(this);
                txt8.setText("-pH value out of normal range");
                txt8.setTextSize(20);
                ll.addView(txt8);
                ph.setTextColor(Color.RED);
            }
            if (num % CONDUCTANCE_OOR == 0) {
                TextView txt9 = new TextView(this);
                txt9.setText("-Conductance value out of normal range");
                txt9.setTextSize(20);
                ll.addView(txt9);
                conductance.setTextColor(Color.RED);
            }
            if (num % TURBIDITY_OOR == 0) {
                TextView txt10 = new TextView(this);
                txt10.setText("-Turbidity value out of normal range");
                txt10.setTextSize(20);
                ll.addView(txt10);
                turbidity.setTextColor(Color.RED);
            }
        }

        ll_main.addView(ll);

        alertDialog.setTitle("ERROR");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();

    }

    private void saveData() {

        SharedPreferences s;
        s = getSharedPreferences("DataFile", 0);

        SharedPreferences.Editor e = s.edit();

        e.putBoolean("stream", stream.isChecked());
        e.putBoolean("bucket", bucket.isChecked());

        // Save data as strings since we'll write em to a file anyway
        e.putString("oxygen", oxygen.getText().toString());
        e.putString("temp", temp.getText().toString());
        e.putString("ph", ph.getText().toString());
        e.putString("conductance", conductance.getText().toString());
        e.putString("turbidity", turbidity.getText().toString());

        e.commit();

    }

    private void badError(){
        View view = this.getCurrentFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);

        ll_main.setOrientation(LinearLayout.VERTICAL);
        ll_main.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });

        TextView txt = new TextView(this);
        txt.setTextSize(20);
        txt.setText("ERROR: pH value is impossible");
        ll_main.addView(txt);

        ll_main.addView(ll);

        alertDialog.setTitle("ERROR");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();
    }

}















