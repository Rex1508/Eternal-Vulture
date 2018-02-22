package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Observations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observations);
    }


    public void onClick(View v) {
        // improves = add things for the other buttons!
        switch (v.getId()) {
            case R.id.btn_Finish: //If e click the finish button, it will complete and go to the home page
                // improves = validate that the ckeckboxes have been clicked
                Intent i = new Intent(Observations.this, Home.class);
                startActivity(i);
                break;
        }
    }
}