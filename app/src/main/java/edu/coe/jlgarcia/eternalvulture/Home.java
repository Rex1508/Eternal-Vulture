package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button collect_sample = (Button) this.findViewById(R.id.btn_Collect_Samples);
        collect_sample.setOnClickListener(this);


    }
/*
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Home.this, InitialDataInfo.class);

        startActivity(i);
    }
    */

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Collect_Samples:

                SharedPreferences s;
                s = getSharedPreferences("DataFile",0);
                SharedPreferences.Editor e = s.edit();

                e.clear();
                e.commit();

                Intent i = new Intent(Home.this, InitialDataInfo.class);
                startActivity(i);
                break;
            case R.id.btn_Review_Samples:
                // open review page //
                Intent j = new Intent(Home.this, InitialDataInfo.class);
                startActivity(j);
                break;

        }

    }
}