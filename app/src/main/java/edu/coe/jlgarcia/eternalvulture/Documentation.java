package edu.coe.jlgarcia.eternalvulture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Documentation extends AppCompatActivity implements View.OnClickListener {

    Button userguide;
    Button devnotes;
    Button instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        idControls();

    }


    private void idControls(){

        userguide = this.findViewById(R.id.btn_userguide);
        devnotes = this.findViewById(R.id.btn_devnotes);
        instructions = this.findViewById(R.id.btn_instructions);

        userguide.setOnClickListener(this);
        devnotes.setOnClickListener(this);
        instructions.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.btn_userguide):

                Uri uri = Uri.parse("https://docs.google.com/document/d/1ATYo-w8xcmRbEY24KxXnpFc_UPfh5LM0dqvomJA1H0k/edit?usp=sharing"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;

            case (R.id.btn_devnotes):
                Uri uri2 = Uri.parse("https://docs.google.com/document/d/1r3b8INuD50r3_ucZnevwpCrXI7jYLD7NcqTAL4IKt6Y/edit?usp=sharing"); // missing 'http://' will cause crashed
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);

                startActivity(intent2);
                break;

            case (R.id.btn_instructions):
                Uri uri3 = Uri.parse("https://docs.google.com/document/d/1obhOSzmD4RCC1boxyfXbeMbVWhrsQ1i9D5nPirxEThk/edit?usp=sharing"); // missing 'http://' will cause crashed
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent3);

                break;
        }
    }
}
