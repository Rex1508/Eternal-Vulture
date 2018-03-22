package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Observations extends AppCompatActivity implements View.OnClickListener{

    Button finish;

    CheckBox highwater;
    CheckBox lowwater;
    CheckBox debris;
    CheckBox sample1;
    CheckBox sample2;
    CheckBox sample3;

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = "Data.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observations);

        idControls();
    }



    private void idControls(){
        finish = this.findViewById(R.id.btn_Finish);
        finish.setOnClickListener(this);

        highwater = this.findViewById(R.id.CB_High_Water);
        lowwater = this.findViewById(R.id.CB_Low_Water);
        debris = this.findViewById(R.id.CB_Debris);
        sample1 = this.findViewById(R.id.CB_Sample1);
        sample2 = this.findViewById(R.id.CB_Sample2);
        sample3 = this.findViewById(R.id.CB_Sample3);

    }



    public void onClick(View v) {
        // improves = add things for the other buttons!
        switch (v.getId()) {
            case R.id.btn_Finish: //If e click the finish button, it will complete and go to the home page
                // improves = validate that the checkboxes have been clicked

                saveData();

                /*
                Intent i = new Intent(Observations.this, Home.class);
                startActivity(i);
                */
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }




    public void saveData() {

        File file = new File(baseDir+fileName);

        SharedPreferences s;
        s = getSharedPreferences("DataFile",0);



        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try {
            try {
                Map<String, ?> allEntries = s.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    fos.write(entry.getValue().toString().getBytes());
                    fos.write(",".getBytes());
                }

                fos.write(String.valueOf(highwater.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(lowwater.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(debris.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(sample1.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(sample2.isChecked()).getBytes());
                fos.write(",".getBytes());
                fos.write(String.valueOf(sample3.isChecked()).getBytes());
                fos.write(",".getBytes());

                fos.write("\n".getBytes());
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

}