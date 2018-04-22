package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jlgarcia on 2/8/2018.
 */

public class Review extends AppCompatActivity {


    Button Back;
// other objects you create that do things




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        idControls();

        //Outline to systematically generate the saved entries
        //1. Check the file to see how many entries are in it
        //2. For loop capped at that many entries
            //a. make a button name and display text based on the sample number (take from the for loop counter)
            //b. assign the button name and text
            //Button buttonView = new Button(this);
            //buttonView.setText("Button");

            //c. assign on click listener \\\\\\\\\\\\\\How do I set the behavior for this?????
            //buttonView.setOnClickListener(mThisButtonListener);
            //d. actually add the button on the layout ----- how do I space these out so they don't stack???
            //layout.addView(buttonView, p);

        //private OnClickListener mThisButtonListener = new OnClickListener() {  /// do this as a seperate function. I already have one, how do I incorporate the generated buttons?
          //  public void onClick(View v) {
            //    Toast.makeText(MainActivity.this, "Hello !",
              //          Toast.LENGTH_LONG).show();
         //   }
        //};

    }

    public void idControls(){

        Back = (Button) this.findViewById(R.id.btn_Back);
        //Back.setOnClickListener(); // for some reason this broken, but I don't really know what it does anyway... I should ask ian

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Back:

                Intent i = new Intent(Review.this, Home.class); //takes us back to the home page
                startActivity(i);
                break;

        }

    }


    private void ShowFile(){
        SharedPreferences s = getSharedPreferences("DataFile",0);
       // String Collector_Name = s.getString(R.string.("collector_name"));
    }




}
