package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener{

    Button collect_sample = (Button) this.findViewById(R.id.BTN_Collect_Samples);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        collect_sample.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Home.this, InitialDataInfo.class);

        startActivity(i);
    }
}
