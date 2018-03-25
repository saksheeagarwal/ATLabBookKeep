package com.example.saksheeagarwal.bookkeep1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText uname;
    EditText pass;
    Button Login;
    Button Register;

    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.login);
        Register = (Button) findViewById(R.id.reg);


        try {
            database = openOrCreateDatabase("Bookkeep", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Loginn (invite VARCHAR(10) PRIMARY KEY, user VARCHAR(20), pass VARCHAR(10));");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            database.execSQL("INSERT INTO Loginn VALUES('" + "stu3" + "', '" + " " + "', '" + " " + "')");
            database.execSQL("INSERT INTO Loginn VALUES('" + "fac3" + "', '" + " " + "', '" + " " + "')");
            Toast.makeText(getApplicationContext(), "Inserted!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            if (e.toString().contains("code 1555"))
                Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_SHORT).show();
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Reg.class);
                startActivity(i);
                setContentView(R.layout.regactivity);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = uname.getText().toString();
                String pwd = pass.getText().toString();
                if (user.equals("") || pwd.equals("") ) {
                    Toast.makeText(getApplicationContext(), "Please enter all details!", Toast.LENGTH_LONG).show();
                }
                Cursor c = database.rawQuery("SELECT * FROM Loginn WHERE TRIM(user) = '"+user.trim()+"' AND TRIM(pass)= '"+pwd.trim()+"'", null);


                if(c.moveToFirst()){
                    String s= c.getString(0);
                    if(s.contains("stu")){
                    Intent i = new Intent(MainActivity.this,StudentHome.class);
                    startActivity(i);
                    setContentView(R.layout.shome);}
                    else{
                        Intent i = new Intent(MainActivity.this,TeacherHome.class);
                        startActivity(i);
                        setContentView(R.layout.thome);
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}