package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Collect_Samples:
                Intent i = new Intent(Home.this, InitialDataInfo.class);
                startActivity(i);
                break;
            case R.id.btn_Review_Samples:
                // open review page //

        }

    }
}