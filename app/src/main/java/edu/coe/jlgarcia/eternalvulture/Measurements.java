package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Measurements extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurments);




        idControls();
    }



    private void idControls(){

        Button btn_next = this.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        RadioButton stream = this.findViewById(R.id.radio_stream);
        RadioButton bucket = this.findViewById(R.id.radio_bucket);
        EditText oxygen = this.findViewById(R.id.oxygen);
        EditText temp = this.findViewById(R.id.temp);
        EditText ph = this.findViewById(R.id.ph);
        EditText conductance = this.findViewById(R.id.conductance);
        EditText turbidity = this.findViewById(R.id.turbidity);


    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next:
                Intent i = new Intent(Measurements.this, Observations.class);

                startActivity(i);
                break;
        }

    }
}
