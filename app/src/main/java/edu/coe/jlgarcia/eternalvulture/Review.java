package edu.coe.jlgarcia.eternalvulture;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private void ShowFile(){
        SharedPreferences s = getSharedPreferences("DataFile",0);
       // String Collector_Name = s.getString(R.string.("collector_name"));
    }




}
