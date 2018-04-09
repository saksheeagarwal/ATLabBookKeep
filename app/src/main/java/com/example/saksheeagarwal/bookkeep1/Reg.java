package com.example.saksheeagarwal.bookkeep1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import es.dmoral.toasty.Toasty;


public class Reg extends AppCompatActivity {


    EditText uname;
    EditText pass;
    EditText inv;
    Button Register;

    SQLiteDatabase database;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regactivity);
        database = openOrCreateDatabase("Bookkeep", MODE_PRIVATE, null);
        uname = (EditText) findViewById(R.id.uname2);
        pass = (EditText) findViewById(R.id.pass2);
        Register = (Button) findViewById(R.id.reg2);
        inv= (EditText) findViewById(R.id.inv);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String invite = inv.getText().toString();
                String user = uname.getText().toString();
                String pwd = pass.getText().toString();
                if (user.equals("") || pwd.equals("") || invite.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all details!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        database.execSQL("UPDATE Loginn SET user='"+user+"', pass='"+pwd+"' WHERE invite='"+invite+"'");
                        Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                        //oast.makeText(getApplicationContext(),"Registered!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Reg.this,MainActivity.class);
                        startActivity(i);
                    }
                    catch (Exception ignore)
                    {
                        Toast.makeText(getApplicationContext()," Not Registered!", Toast.LENGTH_SHORT).show();
                    }




                }

            }
        });
    }
}
