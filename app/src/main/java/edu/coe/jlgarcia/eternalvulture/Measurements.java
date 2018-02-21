package edu.coe.jlgarcia.eternalvulture;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Measurements extends AppCompatActivity implements View.OnClickListener{


    Button btn_next = this.findViewById(R.id.btn_next);
    RadioButton stream = this.findViewById(R.id.radio_stream);
    RadioButton bucket = this.findViewById(R.id.radio_bucket);
    EditText oxygen = this.findViewById(R.id.oxygen);
    EditText temp = this.findViewById(R.id.temp);
    EditText ph = this.findViewById(R.id.ph);
    EditText conductance = this.findViewById(R.id.conductance);
    EditText turbidity = this.findViewById(R.id.turbidity);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurments);




        idControls();
    }



    private void idControls(){
        btn_next.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next:
                if (valid()) {
                    Intent i = new Intent(Measurements.this, Observations.class);

                    startActivity(i);
                }
                else{error();}
                break;
        }

    }


    private boolean valid(){
        boolean valid = true;

        if ((!stream.isChecked()) && (!bucket.isChecked())){valid = false;}
        if (oxygen.getText().toString().equals("")){valid = false;}
        if (ph.getText().toString().equals("")){valid = false;}
        if (conductance.getText().toString().equals("")){valid = false;}
        if (temp.getText().toString().equals("")){valid = false;}
        if (turbidity.getText().toString().equals("")){valid = false;}

        float ph_f = Float.parseFloat(ph.getText().toString());

        if (ph_f < 0 || ph_f > 14){valid = false;}



        return valid;
    }


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
        txt.setText("Make sure the inputted data is correct");
        ll_main.addView(txt);

        alertDialog.setTitle("ERROR");

        // show it
        alertDialog.setView(ll_main);
        alertDialog.show();

    }
}
