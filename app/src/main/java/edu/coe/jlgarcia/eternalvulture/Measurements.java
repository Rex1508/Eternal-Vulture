package edu.coe.jlgarcia.eternalvulture;

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

public class Measurements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurments);


        Button btn_next = this.findViewById(R.id.btn_next);
        RadioButton stream = this.findViewById(R.id.radio_stream);
        RadioButton bucket = this.findViewById(R.id.radio_bucket);
        EditText oxygen = this.findViewById(R.id.oxygen);
        EditText temp = this.findViewById(R.id.temp);
        EditText ph = this.findViewById(R.id.ph);
        EditText conductance = this.findViewById(R.id.conductance);
        EditText turbidity = this.findViewById(R.id.turbidity);






    }
}
