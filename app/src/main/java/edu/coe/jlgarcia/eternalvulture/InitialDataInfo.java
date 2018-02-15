package edu.coe.jlgarcia.eternalvulture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;



public class InitialDataInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_data_info);

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


        btn_autofill = (Button) this.findViewById(R.id.btn_autofill);
        btn_next = (Button) this.findViewById(R.id.btn_next);
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

}
