package com.example.saksheeagarwal.bookkeep1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentHome extends AppCompatActivity {

    Button search, seerec, rate, profile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shome);


        search = (Button) findViewById(R.id.search);
        seerec = (Button) findViewById(R.id.seerec);
        rate= (Button) findViewById(R.id.rate);
        profile = (Button) findViewById(R.id.profile);




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StudentHome.this,search.class);
                startActivity(i);
                setContentView(R.layout.search);

            }
        });
        seerec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StudentHome.this,SeeRec.class);
                startActivity(i);
                setContentView(R.layout.seerecom);

            }
        });




    }
}
